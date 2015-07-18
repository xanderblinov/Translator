package com.xanderblinov.translator.network;


import android.content.Context;

import com.xanderblinov.translator.R;
import com.xanderblinov.translator.data.TranslatorError;
import com.xanderblinov.translator.network.exception.ApiKeyException;
import com.xanderblinov.translator.network.exception.LimitException;
import com.xanderblinov.translator.network.exception.NoNetworkException;
import com.xanderblinov.translator.network.exception.ServerErrorException;
import com.xanderblinov.translator.network.exception.TranslationException;
import com.xanderblinov.translator.network.exception.YandexApiException;

/**
 * Date: 3/9/2015
 * Time: 8:19 PM
 *
 * @author xanderblinov
 */
public class ErrorProcessor
{
	public static TranslatorError getError(final Exception exception, final Context context)
	{
		if (exception instanceof YandexApiException)
		{
			if (exception instanceof ApiKeyException)
			{
				return TranslatorError.newInstance(context, exception, R.string.api_key_error_title, R.string.api_key_error_message);
			}
			else if (exception instanceof LimitException)
			{

				return TranslatorError.newInstance(context, exception, R.string.limit_error_title, R.string.limit_error_message);
			}
			else if (exception instanceof TranslationException)
			{
				switch (((TranslationException) exception).getYandexError())
				{
					case ERR_TEXT_TOO_LONG:
						return TranslatorError.newInstance(context, exception, R.string.translation_text_error_title,
								R.string.translation_text_too_long_error_message);
					case ERR_UNPROCESSABLE_TEXT:
						return TranslatorError.newInstance(context, exception, R.string.translation_text_error_title,
								R.string.translation_text_unproceccable_error_message);
					case ERR_LANG_NOT_SUPPORTED:
						return TranslatorError.newInstance(context, exception, R.string.translation_text_error_title,
								R.string.translation_text_lang_not_supported_error_message);
				}
			}
		}

		if (exception instanceof NoNetworkException)
		{
			return TranslatorError.newInstance(context, exception, R.string.no_network_error_title, R.string.no_network_error_message);
		}

		if (exception instanceof ServerErrorException)
		{
			return TranslatorError.newInstance(context, exception, R.string.server_error_title, R.string.server_error_message);
		}

		return TranslatorError.newInstance(context, exception, R.string.unknown_error_title, R.string.unknown_error_message);

	}
}
