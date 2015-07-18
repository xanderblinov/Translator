package com.xanderblinov.translator.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xanderblinov.translator.data.YandexErrorResponse;
import com.xanderblinov.translator.network.exception.ApiKeyException;
import com.xanderblinov.translator.network.exception.LimitException;
import com.xanderblinov.translator.network.exception.NoNetworkException;
import com.xanderblinov.translator.network.exception.ServerErrorException;
import com.xanderblinov.translator.network.exception.TranslationException;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Date: 3/8/2015
 * Time: 5:21 PM
 *
 * @author xanderblinov
 */
public class YandexErrorHandler implements ErrorHandler
{
	@Override
	public Throwable handleError(RetrofitError cause)
	{
		switch (cause.getKind())
		{
			case NETWORK:
				return new NoNetworkException(cause);
			case  CONVERSION:
			case UNEXPECTED:
				return new ServerErrorException(cause);

		}

		YandexErrorResponse errorResponse;
		try
		{
			String json = new String(((TypedByteArray) cause.getResponse().getBody()).getBytes());
			errorResponse = new Gson().fromJson(json, YandexErrorResponse.class);
		}
		catch (NullPointerException | JsonSyntaxException e)
		{
			return cause;
		}

		YandexError error = YandexError.getError(errorResponse.getCode());
		switch (error)
		{
			case ERR_KEY_INVALID:
			case ERR_KEY_BLOCKED:
				return new ApiKeyException(cause, error);

			case ERR_DAILY_CHAR_LIMIT_EXCEEDED:
			case ERR_DAILY_REQ_LIMIT_EXCEEDED:
				return new LimitException(cause, error);

			case ERR_LANG_NOT_SUPPORTED:
			case ERR_TEXT_TOO_LONG:
			case ERR_UNPROCESSABLE_TEXT:
				return new TranslationException(cause, error);
			case ERR_UNKNOWN:
				return cause;

		}
		return cause;
	}
}
