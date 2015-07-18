package com.xanderblinov.translator.async;

import java.util.List;
import java.util.Locale;

import android.support.v4.content.AsyncTaskLoader;

import com.xanderblinov.translator.app.TranslatorApp;
import com.xanderblinov.translator.app.TranslatorConfig;
import com.xanderblinov.translator.data.GetLanguagesResult;
import com.xanderblinov.translator.data.Language;
import com.xanderblinov.translator.network.exception.NoNetworkException;
import com.xanderblinov.translator.network.exception.ServerErrorException;
import com.xanderblinov.translator.network.exception.YandexApiException;
import com.xanderblinov.translator.network.response.GetLanguagesResponse;
import com.xanderblinov.translator.utils.TranslatorPrefs;
import retrofit.RetrofitError;

/**
 * Date: 3/7/2015
 * Time: 10:38 AM
 *
 * @author xanderblinov
 */
public class GetLanguagesLoader extends AsyncTaskLoader<ComplexResult<GetLanguagesResult>>
{
	public GetLanguagesLoader()
	{
		super(TranslatorApp.get());
	}

	@Override
	public ComplexResult<GetLanguagesResult> loadInBackground()
	{

		GetLanguagesResponse response = null;
		try
		{
			response = TranslatorApp.get().getApiClient().getLangs(Locale.getDefault().getLanguage());
		}
		catch (YandexApiException | RetrofitError | NoNetworkException | ServerErrorException exception)
		{
			if (!TranslatorApp.get().getDbClient().isDeployed())
			{
				return new ComplexResult<>(exception);
			}
		}
		List<Language> languages;
		Language language;
		try
		{
			if (response != null)
			{
				TranslatorApp.get().getDbClient().saveLanguages(response.getLanguages(), response.getDirrections());
			}
			language = TranslatorApp.get().getDbClient().getLanguage(TranslatorPrefs.getLastLanguage(getContext()).getName());
			languages = TranslatorApp.get().getDbClient().getSupportLanguages(TranslatorConfig.sDefaultLanguage);

		}
		catch (Exception error)
		{
			return new ComplexResult<>(error);
		}

		return new ComplexResult<>(new GetLanguagesResult(languages, language));
	}

}
