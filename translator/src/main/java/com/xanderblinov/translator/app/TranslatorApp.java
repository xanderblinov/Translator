package com.xanderblinov.translator.app;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xanderblinov.translator.db.DbClient;
import com.xanderblinov.translator.network.YandexErrorHandler;
import com.xanderblinov.translator.network.YandexFieldNamePolicy;
import com.xanderblinov.translator.network.YandexRequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Date: 3/7/2015
 * Time: 10:41 AM
 *
 * @author xanderblinov
 */
public class TranslatorApp extends Application
{
	private static volatile TranslatorApp sInstance;

	public TranslatorApp()
	{
		sInstance = this;
	}

	private YandexTranslateApi mApiClient;

	private DbClient mDbClient;

	@Override
	public void onCreate()
	{
		super.onCreate();
		onApplicationCreate();
	}

	public void onApplicationCreate()
	{
		TranslatorLog.enable();

		// @formatter:off
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setFieldNamingStrategy(new YandexFieldNamePolicy())
				.setPrettyPrinting()
				.serializeNulls()
				.create();

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(TranslatorConfig.sYandexBaseUrl)
				.setConverter(new GsonConverter(gson))
				.setLogLevel(TranslatorLog.getRestLogLevel())
				.setRequestInterceptor(new YandexRequestInterceptor())
				.setErrorHandler(new YandexErrorHandler())
				.build();
		// @formatter:on

		mApiClient = restAdapter.create(YandexTranslateApi.class);

		mDbClient = new DbClient(this);
	}

	public static TranslatorApp get()
	{
		return sInstance;
	}

	public YandexTranslateApi getApiClient()
	{
		return mApiClient;
	}

	public DbClient getDbClient()
	{
		return mDbClient;
	}

}