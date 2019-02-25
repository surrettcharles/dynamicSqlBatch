package me.charlessurrett.dynamicSqlBatch.processor;

import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import me.charlessurrett.dynamicSqlBatch.dao.PersonSqlProvider;
import me.charlessurrett.dynamicSqlBatch.domain.Person;

@Service
public class PersonProcessor implements ItemProcessor<Person, UpdateStatementProvider>
{
	private static final Logger log = LoggerFactory.getLogger(PersonProcessor.class);

	@Override
	public UpdateStatementProvider process(final Person item) throws Exception
	{
		final String firstName = item.getFirstName().toUpperCase();
		final String lastName = item.getLastName().toUpperCase();
		
		final Person transformed = new Person(item.getId(), firstName, lastName);
		
		log.info("Converting (" + item + ") into (" + transformed + ")");
		
		return PersonSqlProvider.updatePerson(transformed);
	}
	
}
