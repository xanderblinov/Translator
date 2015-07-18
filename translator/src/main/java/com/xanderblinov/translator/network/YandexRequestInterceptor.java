package com.xanderblinov.translator.network;

import com.xanderblinov.translator.app.TranslatorConfig;
import retrofit.RequestInterceptor;

/**
 * Date: 3/8/2015
 * Time: 6:31 PM
 *
 * @author xanderblinov
 */
public class YandexRequestInterceptor implements RequestInterceptor
{
	@Override
	public void intercept(final RequestFacade requestFacade)
	{
		requestFacade.addQueryParam("key", TranslatorConfig.sYandexApiKey);
	}
}
