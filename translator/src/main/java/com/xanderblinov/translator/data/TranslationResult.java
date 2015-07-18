package com.xanderblinov.translator.data;

/**
 * Date: 3/8/2015
 * Time: 4:28 PM
 *
 * @author xanderblinov
 */
public class TranslationResult
{
	private final boolean mPreTranslation;
	private final String mFromText;
	private final String mToText;

	public TranslationResult(final boolean preTranslation, final String fromText, final String toText)
	{
		mPreTranslation = preTranslation;
		mFromText = fromText;
		mToText = toText;
	}

	public boolean isPreTranslation()
	{
		return mPreTranslation;
	}

	public String getFromText()
	{
		return mFromText;
	}

	public String getToText()
	{
		return mToText;
	}
}
