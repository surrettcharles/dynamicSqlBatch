package me.charlessurrett.dynamicSqlBatch.dao;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

import me.charlessurrett.dynamicSqlBatch.domain.Person;

public class PersonSqlProvider
{
	public static SelectStatementProvider getPeopleWithLastName(String lastName)
	{
		PersonTable person = new PersonTable();
		
		return select(
				person.personId,
				person.firstName,
				person.lastName)
				.from(person)
				.where(person.lastName, isEqualToWhenPresent(lastName))
				.build()
				.render(RenderingStrategy.MYBATIS3);
	}
	
	public static UpdateStatementProvider updatePerson(Person item)
	{
		PersonTable person = new PersonTable();
		
		return update(person)
				.set(person.firstName).equalTo(item.getFirstName())
				.set(person.lastName).equalTo(item.getLastName())
				.where(person.personId, isEqualToWhenPresent(item.getId()))
				.build()
				.render(RenderingStrategy.MYBATIS3);
	}
	
	private PersonSqlProvider()
	{
		// prevent instantiating this static class
	}
}
