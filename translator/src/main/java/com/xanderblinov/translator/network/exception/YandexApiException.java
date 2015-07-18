package com.xanderblinov.translator.network.exception;

import com.xanderblinov.translator.network.YandexError;

/**
 * Date: 3/8/2015
 * Time: 5:39 PM
 *
 * @author xanderblinov
 */
public class YandexApiException extends Exception
{
	protected final YandexError mYandexError;

	public YandexApiException(final YandexError yandexError)
	{
		mYandexError = yandexError;
	}

	public YandexApiException(final String detailMessage, final YandexError yandexError)
	{
		super(detailMessage);
		mYandexError = yandexError;
	}

	public YandexApiException(final String detailMessage, final Throwable throwable, final YandexError yandexError)
	{
		super(detailMessage, throwable);
		mYandexError = yandexError;
	}

	public YandexApiException(final Throwable throwable, final YandexError yandexError)
	{
		super(throwable);
		mYandexError = yandexError;
	}

	public YandexError getYandexError()
	{
		return mYandexError;
	}
}
