package com.xanderblinov.translator.async;

import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.xanderblinov.translator.app.TranslatorApp;
import com.xanderblinov.translator.app.TranslatorConfig;
import com.xanderblinov.translator.data.Language;
import com.xanderblinov.translator.data.TranslationResult;
import com.xanderblinov.translator.network.exception.NoNetworkException;
import com.xanderblinov.translator.network.exception.ServerErrorException;
import com.xanderblinov.translator.network.exception.YandexApiException;
import com.xanderblinov.translator.network.response.TranslateResponse;
import retrofit.RetrofitError;

/**
 * Date: 3/7/2015
 * Time: 10:38 AM
 *
 * @author xanderblinov
 */
public class TranslateLoader extends AsyncTaskLoader<ComplexResult<TranslationResult>>
{

	private final boolean mPreTranslation;
	private final String mTextFrom;
	private final Language mFromLang;

	public TranslateLoader(final boolean preTranslation, final String textFrom, final Language fromLang)
	{
		super(TranslatorApp.get());
		mPreTranslation = preTranslation;
		mTextFrom = textFrom;
		mFromLang = fromLang;
	}

	@Override
	public ComplexResult<TranslationResult> loadInBackground()
	{
		if (TextUtils.isEmpty(mTextFrom))
		{
			return new ComplexResult<>(new TranslationResult(mPreTranslation, mTextFrom, ""));
		}

		final String languages =
				mFromLang == null ? TranslatorConfig.sDefaultLanguage : mFromLang.getName() + "-" + TranslatorConfig.sDefaultLanguage;

		TranslateResponse response;
		try
		{
			response = TranslatorApp.get().getApiClient().translate(languages, new String[]{mTextFrom});
		}
		catch (YandexApiException | RetrofitError | NoNetworkException | ServerErrorException exception)
		{
			return new ComplexResult<>(exception);
		}

		try
		{
			if (!mPreTranslation)
			{
				TranslatorApp.get().getDbClient().saveWord(response.getFromLang(), mTextFrom, response.getText());
			}

		}
		catch (Exception error)
		{
			return new ComplexResult<>(error);
		}

		return new ComplexResult<>(new TranslationResult(mPreTranslation, mTextFrom, response.getText()));
	}
}
