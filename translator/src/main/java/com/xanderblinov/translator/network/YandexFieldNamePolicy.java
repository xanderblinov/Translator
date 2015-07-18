package com.xanderblinov.translator.network;

import java.lang.reflect.Field;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;

/**
 * Date: 3/8/2015
 * Time: 6:15 PM
 *
 * @author xanderblinov
 */
public class YandexFieldNamePolicy implements FieldNamingStrategy
{
	@Override
	public String translateName(Field field)
	{
		String name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
		name = name.substring(2, name.length()).toLowerCase();
		return name;
	}
}