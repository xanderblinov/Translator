package com.xanderblinov.translator.network.exception;

import com.xanderblinov.translator.network.YandexError;

/**
 * Date: 3/8/2015
 * Time: 5:40 PM
 *
 * @author xanderblinov
 */
public class TranslationException extends YandexApiException
{
	public TranslationException(final YandexError yandexError)
	{
		super(yandexError);
	}

	public TranslationException(final String detailMessage,final YandexError yandexError)
	{
		super(detailMessage, yandexError);
	}

	public TranslationException(final String detailMessage, final Throwable throwable,final YandexError yandexError)
	{
		super(detailMessage, throwable, yandexError);
	}

	public TranslationException(final Throwable throwable,final YandexError yandexError)
	{
		super(throwable, yandexError);
	}
}
