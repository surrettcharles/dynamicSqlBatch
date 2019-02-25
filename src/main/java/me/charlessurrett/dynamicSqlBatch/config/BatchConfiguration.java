package me.charlessurrett.dynamicSqlBatch.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.charlessurrett.dynamicSqlBatch.dao.PersonSqlProvider;
import me.charlessurrett.dynamicSqlBatch.domain.Person;
import me.charlessurrett.dynamicSqlBatch.mybatis.MyBatisDynamicCursorItemReader;
import me.charlessurrett.dynamicSqlBatch.processor.PersonProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration
{
	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private PersonProcessor processor;

	@Bean
	public SelectStatementProvider peopleStatement(@Value("${lastName:#{null}}") String lastName)
	{
		SelectStatementProvider statement = PersonSqlProvider.getPeopleWithLastName(lastName);

		log.info("Rendered SQL: " + statement.getSelectStatement());

		return statement;
	}

	@Bean
	public MyBatisCursorItemReader<Person> reader(SqlSessionFactory sqlSessionFactory, SelectStatementProvider peopleStatement)
	{
		MyBatisDynamicCursorItemReader<Person> reader = new MyBatisDynamicCursorItemReader<>();
		reader.setName("personReader");
		reader.setQueryId("me.charlessurrett.dynamicSqlBatch.dao.PersonMapper.getPeopleWithLastName");
		reader.setSqlSessionFactory(sqlSessionFactory);

		Map<String, Object> params = new HashMap<>();
		params.put("statement", peopleStatement);

		reader.setParameterValues(params);

		return reader;
	}

	@Bean
	public MyBatisBatchItemWriter<UpdateStatementProvider> writer(SqlSessionFactory sqlSessionFactory)
	{
		MyBatisBatchItemWriter<UpdateStatementProvider> writer = new MyBatisBatchItemWriter<>();
		writer.setSqlSessionFactory(sqlSessionFactory);
		writer.setStatementId("me.charlessurrett.dynamicSqlBatch.dao.PersonMapper.updatePerson");

		return writer;
	}

	@Bean
	public Job importUserJob(Step step1)
	{
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1)
				.end()
				.build();
	}

	@Bean
	public Step step1(ItemReader<Person> reader, ItemWriter<UpdateStatementProvider> writer)
	{
		return stepBuilderFactory.get("step1")
				.<Person, UpdateStatementProvider> chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}
}
