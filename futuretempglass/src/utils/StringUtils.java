package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils{

	public static char getRandomLetterOrNumber()
	{
		int randomNum = (int)(Math.random() * 62) + 1;
		if(randomNum > 36)
		{
			randomNum += 60;
		}
		else if(randomNum > 10)
		{
			randomNum += 54;
		}
		else
		{
			randomNum += 47;
		}
		return (char)randomNum;// 48-57, 65-90, 97-122
		// 10+26+26
	}

	public static String getRandomStringOfLettersAndNumbers(int length)
	{
		String random = "";
		for(int i = 0; i < length; i++)
		{
			random += getRandomLetterOrNumber();
		}
		return random;
	}
	
	public static String listToString(List<?> list)
	{
		if(list == null)
		{
			return null;
		}
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for(Object object: list)
		{
			if(!first)
			{
				builder.append(", ");
			}
			first = false;
			builder.append(object.toString());
		}
		return builder.toString();
	}
	
	public static List<String> stringToList(String string)
	{
		if(string == null)
		{
			return null;
		}
		if(string == "")
		{
			return new ArrayList<String>();
		}
		String[] stringArray = string.split(", ");
		List<String> stringList = new ArrayList<String>();
		for(String s: stringArray)
		{
			stringList.add(s);
		}
		return stringList;
	}
	
	public static boolean isEmpty(String string)
	{
		if(string == null)
		{
			return true;
		}
		return "".equals(string.trim());
	}

	public static void main(String args[])
	{
		long startTime = System.currentTimeMillis();
		List<String> strings = new ArrayList<String>();
		while (true)
		{
			String string = getRandomStringOfLettersAndNumbers(8);
			if(strings.contains(string))
			{
				break;
			}
			System.out.println(string);
			strings.add(string);
		}
		System.out.println(strings.size());
		System.out.println((System.currentTimeMillis() - startTime)/1000);
	}

}
