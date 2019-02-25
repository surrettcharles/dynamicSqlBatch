package me.charlessurrett.dynamicSqlBatch.mybatis;

import org.apache.ibatis.annotations.Param;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

public class BatchSqlProviderAdapter extends SqlProviderAdapter
{
	@Override
	public String select(@Param("statement") SelectStatementProvider selectStatement)
	{
		return super.select(selectStatement);
	}
}
