package com.xanderblinov.translator.network;

/**
 * Date: 3/8/2015
 * Time: 5:59 PM
 *
 * @author xanderblinov
 */
public enum YandexError
{

	ERR_KEY_INVALID(401, "Invalid API key."),
	ERR_KEY_BLOCKED(402, "This API key has been blocked."),
	ERR_DAILY_REQ_LIMIT_EXCEEDED(403, "You have reached the daily limit for requests (including calls of the detect method)."),
	ERR_DAILY_CHAR_LIMIT_EXCEEDED(404, "You have reached the daily limit for the volume of translated text (including calls of the detect method)."),
	ERR_TEXT_TOO_LONG(413, "The text size exceeds the maximum."),
	ERR_UNPROCESSABLE_TEXT(422, "The text could not be translated."),
	ERR_LANG_NOT_SUPPORTED(501, "The specified translation direction is not supported."),
	ERR_UNKNOWN(-1, "Unknown error");

	private final String mDescription;
	private final int mCode;


	YandexError(final int code, final String description)
	{
		mCode = code;
		mDescription = description;
	}

	public static YandexError getError(int code)
	{
		for (YandexError error : values())
		{
			if (code == error.mCode)
			{
				return error;
			}
		}
		return ERR_UNKNOWN;
	}
}
