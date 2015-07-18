package com.xanderblinov.translator.network.response;

import android.text.TextUtils;

/**
 * Date: 3/8/2015
 * Time: 5:07 PM
 *
 * @author xanderblinov
 */
public class TranslateResponse
{
	private int mCode;
	private String mLang;
	private String[] mText;

	public int getCode()
	{
		return mCode;
	}

	private String getFromPart(int id)
	{
		if (TextUtils.isEmpty(mLang))
		{
			return "";
		}
		String[] parts = mLang.split("-");

		if (parts.length != 2)
		{
			return "";
		}

		return parts[id];
	}

	public String getFromLang()
	{
		return getFromPart(0);
	}


	public String getToLang()
	{
		return getFromPart(1);
	}

	public String getText()
	{
		return (mText == null || mText.length == 0) ? "" : mText[0];
	}
}
