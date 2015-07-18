package com.xanderblinov.translator.async;

import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.xanderblinov.translator.app.TranslatorApp;
import com.xanderblinov.translator.data.Language;
import com.xanderblinov.translator.utils.TranslatorPrefs;

/**
 * Date: 3/7/2015
 * Time: 10:38 AM
 *
 * @author xanderblinov
 */
public class GetWordsLoader extends AsyncTaskLoader<ComplexResult<Cursor>>
{

	private final String mString;

	public GetWordsLoader(final String string)
	{
		super(TranslatorApp.get());
		mString = string;
	}

	@Override
	public ComplexResult<Cursor> loadInBackground()
	{
		Language language = TranslatorPrefs.getLastLanguage(getContext());

		Cursor cursor  = null;
		try
		{
			cursor = TranslatorApp.get().getDbClient().getWords(language, mString);
		}
		catch (Exception error)
		{
			return new ComplexResult<>(error);
		}

		return new ComplexResult<>(cursor);
	}
}
