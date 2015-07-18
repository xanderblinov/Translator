package com.xanderblinov.translator.network.exception;

import com.xanderblinov.translator.network.YandexError;

/**
 * Date: 3/8/2015
 * Time: 5:38 PM
 *
 * @author xanderblinov
 */
public class LimitException extends YandexApiException
{
	public LimitException(final YandexError yandexError)
	{
		super(yandexError);
	}

	public LimitException(final String detailMessage,final YandexError yandexError)
	{
		super(detailMessage, yandexError);
	}

	public LimitException(final String detailMessage, final Throwable throwable,final YandexError yandexError)
	{
		super(detailMessage, throwable, yandexError);
	}

	public LimitException(final Throwable throwable,final YandexError yandexError)
	{
		super(throwable, yandexError);
	}
}
