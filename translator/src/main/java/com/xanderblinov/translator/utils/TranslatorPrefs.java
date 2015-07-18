package com.xanderblinov.translator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xanderblinov.translator.app.TranslatorConfig;
import com.xanderblinov.translator.data.Language;

/**
 * Date: 3/9/2015
 * Time: 10:52 AM
 *
 * @author xanderblinov
 */
public class TranslatorPrefs
{
	private static final String PREFS = "translator.prefs";

	private static final String KEY_LAST_LANGUAGE_NAME = "lastLanguage";
	private static final String KEY_LAST_LANGUAGE_DESCRIPTION = "lastLanguageDescription";


	public static SharedPreferences getPreferences(Context context)
	{
		return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
	}

	public static void setLastLanguage(Context context, Language language)
	{
		getPreferences(context).edit().putString(KEY_LAST_LANGUAGE_NAME, language.getName())
				.putString(KEY_LAST_LANGUAGE_DESCRIPTION, language.getDisplayName()).apply();
	}

	public static Language getLastLanguage(Context context)
	{
		SharedPreferences preferences = getPreferences(context);
		return new Language(preferences.getString(KEY_LAST_LANGUAGE_NAME, TranslatorConfig.sDefaultTranslationLanguage),
				preferences.getString(KEY_LAST_LANGUAGE_DESCRIPTION, TranslatorConfig.sDefaultTranslationLanguageDescription));
	}

}
