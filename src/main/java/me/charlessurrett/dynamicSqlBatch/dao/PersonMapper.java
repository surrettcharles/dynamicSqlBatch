package me.charlessurrett.dynamicSqlBatch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

import me.charlessurrett.dynamicSqlBatch.domain.Person;
import me.charlessurrett.dynamicSqlBatch.mybatis.BatchSqlProviderAdapter;

@Mapper
public interface PersonMapper
{
	@Results(id = "personResult", value = {
			@Result(property = "id", column = "person_id", id = true),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "lastName", column = "last_name")
	})
	@SelectProvider(type = BatchSqlProviderAdapter.class, method = "select")
	List<Person> getPeopleWithLastName(SelectStatementProvider statement);
	
	@UpdateProvider(type = BatchSqlProviderAdapter.class, method = "update")
	int updatePerson(UpdateStatementProvider statement);
}
