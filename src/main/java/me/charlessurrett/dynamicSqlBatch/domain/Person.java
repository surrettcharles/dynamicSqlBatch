package me.charlessurrett.dynamicSqlBatch.domain;

public class Person
{
	private int id;
	private String firstName;
	private String lastName;
	
	public Person()
	{
		super();
	}

	public Person(int id, String firstName, String lastName)
	{
		this();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	@Override
	public String toString()
	{
		return String.format("firstName: %s, lastName: %s", firstName, lastName);
	}
}
