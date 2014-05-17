package com.aplc.dotarthstone;

public class Action
{
	public String functionName;
	public String[] args;

	public Action(String name, String[] arguments)
	{
		functionName = name;
		args = arguments;
	}
}