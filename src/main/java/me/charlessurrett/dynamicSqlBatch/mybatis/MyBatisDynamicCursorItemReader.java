package me.charlessurrett.dynamicSqlBatch.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.spring.batch.MyBatisCursorItemReader;

public class MyBatisDynamicCursorItemReader<T extends Object> extends MyBatisCursorItemReader<T>
{
	@Override
	public void setParameterValues(Map<String, Object> parameterValues)
	{
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.putAll(parameterValues);
		
		if(parameterValues.containsKey("statement"))
		{
			SelectStatementProvider statement = (SelectStatementProvider)parameterValues.get("statement");
			parameters.put("parameters", statement.getParameters());
		}
		
		super.setParameterValues(parameters);
	}
}
