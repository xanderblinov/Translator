package com.xanderblinov.translator.app;

import java.sql.SQLException;

import android.util.Log;

import retrofit.RestAdapter;


public class TranslatorLog
{
	public static final String TAG = "Translator";

	private static boolean sEnabled = false;

	public static void log(String message)
	{
		log(message, null);
	}

	public static void log(String message, Exception e)
	{
		log(TAG, message, e);
	}

	public static void log(String tag, String message, Exception e)
	{
		if (!sEnabled)
		{
			return;
		}

		Log.d(tag, message, e);
	}

	public static void enable()
	{
		sEnabled = true;
	}

	public static void disable()
	{
		sEnabled = false;
	}

	public static RestAdapter.LogLevel getRestLogLevel()
	{
		return sEnabled ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE;
	}

	public static void log(final SQLException e)
	{
		log(e.getMessage(), e);
	}
}
