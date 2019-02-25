package me.charlessurrett.dynamicSqlBatch.dao;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class PersonTable extends SqlTable
{
	public final SqlColumn<Integer> personId = column("person_id", JDBCType.BIGINT);
	public final SqlColumn<String> firstName = column("first_name", JDBCType.VARCHAR);
	public final SqlColumn<String> lastName = column("last_name", JDBCType.VARCHAR);

	protected PersonTable()
	{
		super("people");
	}
}
