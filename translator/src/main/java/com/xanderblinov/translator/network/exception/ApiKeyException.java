package com.xanderblinov.translator.network.exception;

import com.xanderblinov.translator.network.YandexError;

/**
 * Date: 3/8/2015
 * Time: 5:27 PM
 *
 * @author xanderblinov
 */
public class ApiKeyException extends YandexApiException
{
	public ApiKeyException(final YandexError yandexError)
	{
		super(yandexError);
	}

	public ApiKeyException(final String detailMessage,final YandexError yandexError)
	{
		super(detailMessage, yandexError);
	}

	public ApiKeyException(final String detailMessage, final Throwable throwable,final YandexError yandexError)
	{
		super(detailMessage, throwable, yandexError);
	}

	public ApiKeyException(final Throwable throwable,final YandexError yandexError)
	{
		super(throwable, yandexError);
	}
}
