package com.xanderblinov.translator.app;

import com.xanderblinov.translator.network.exception.NoNetworkException;
import com.xanderblinov.translator.network.exception.ServerErrorException;
import com.xanderblinov.translator.network.exception.YandexApiException;
import com.xanderblinov.translator.network.response.GetLanguagesResponse;
import com.xanderblinov.translator.network.response.TranslateResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Date: 3/7/2015
 * Time: 10:39 AM
 *
 * @author xanderblinov
 */
public interface YandexTranslateApi
{
	@GET("/getLangs")
	GetLanguagesResponse getLangs(@Query("ui") String uk) throws YandexApiException, NoNetworkException, ServerErrorException;

	@GET("/translate")
	TranslateResponse translate(@Query("lang") String lang, @Query("text") String[] text) throws YandexApiException, NoNetworkException, ServerErrorException;
}
