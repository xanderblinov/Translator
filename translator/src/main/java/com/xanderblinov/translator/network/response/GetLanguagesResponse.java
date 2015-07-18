package com.xanderblinov.translator.network.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xanderblinov.translator.data.Direction;
import com.xanderblinov.translator.data.Language;

/**
 * Date: 3/7/2015
 * Time: 11:13 AM
 *
 * @author xanderblinov
 */
public class GetLanguagesResponse
{
	private ArrayList<String> mDirs;

	private HashMap<String, String> mLangs;


	public List<Language> getLanguages()
	{
		List<Language> languages = new ArrayList<>();

		if (mLangs == null)
		{
			return languages;
		}


		for (String key : mLangs.keySet())
		{
			languages.add(new Language(key, mLangs.get(key)));
		}

		return languages;
	}

	public List<Direction> getDirrections()
	{
		List<Direction> directions = new ArrayList<>();

		if (mDirs == null)
		{
			return directions;
		}

		String[] parts;
		for (String string : mDirs)
		{

			parts = string.split("-");

			if (parts.length != 2)
			{
				continue;
			}

			directions.add(new Direction(parts[0], parts[1]));

		}

		return directions;
	}

}
