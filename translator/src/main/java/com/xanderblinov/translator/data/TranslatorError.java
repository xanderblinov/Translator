package com.xanderblinov.translator.data;

import android.content.Context;

/**
 * Date: 3/9/2015
 * Time: 8:20 PM
 *
 * @author xanderblinov
 */
public class TranslatorError
{
	private Exception mException;
	private String mTitle;
	private String mMessage;

	public TranslatorError(final Exception exception, final String title, final String message)
	{
		mException = exception;
		mTitle = title;
		mMessage = message;
	}

	public Exception getException()
	{
		return mException;
	}

	public String getTitle()
	{
		return mTitle;
	}

	public String getMessage()
	{
		return mMessage;
	}

	public static TranslatorError newInstance(final Context context, final Exception exception, final int titleResId, final int messageResId)
	{
		return new TranslatorError(exception, context.getString(titleResId), context.getString(messageResId));
	}
}
