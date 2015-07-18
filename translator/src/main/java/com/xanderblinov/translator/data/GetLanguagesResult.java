package com.xanderblinov.translator.data;

import java.util.List;

/**
 * Date: 3/9/2015
 * Time: 11:22 AM
 *
 * @author xanderblinov
 */
public class GetLanguagesResult
{
	private List<Language> mLanguages;
	private Language mLastLanguage;

	public GetLanguagesResult(final List<Language> languages, final Language lastLanguage)
	{
		mLanguages = languages;
		mLastLanguage = lastLanguage;
	}

	public List<Language> getLanguages()
	{
		return mLanguages;
	}

	public Language getLastLanguage()
	{
		return mLastLanguage;
	}
}
