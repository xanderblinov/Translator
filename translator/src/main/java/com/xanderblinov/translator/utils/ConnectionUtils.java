package com.xanderblinov.translator.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xanderblinov.translator.app.TranslatorApp;

/**
 * Date: 3/7/2015
 * Time: 3:24 PM
 *
 * @author xanderblinov
 */
public class ConnectionUtils
{
	public static boolean isOnline()
	{
		final Context context = TranslatorApp.get().getBaseContext();

		if (context == null)
		{
			return true;
		}

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
}
