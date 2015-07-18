package com.xanderblinov.translator.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xanderblinov.translator.R;
import com.xanderblinov.translator.async.ComplexResult;
import com.xanderblinov.translator.async.TranslateLoader;
import com.xanderblinov.translator.data.Language;
import com.xanderblinov.translator.data.TranslationResult;
import com.xanderblinov.translator.network.ErrorProcessor;
import com.xanderblinov.translator.utils.TranslatorPrefs;

/**
 * Date: 3/7/2015
 * Time: 2:43 PM
 *
 * @author xanderblinov
 */

/**
 * Date: 3/7/2015
 * Time: 2:43 PM
 *
 * @author xanderblinov
 */


public class AddWordActivity extends BaseWordActivity implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener
{
	private static final int TRANSLATE_LOADER_ID = 2;

	private Language mFromLanguage;
	private static final String sFromKey = "FromKey";


	private TextView mTranslationText;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_add_word);

		mFromLanguage = TranslatorPrefs.getLastLanguage(this);

		setHeader(getString(R.string.add_word_reg, mFromLanguage.getDisplayName()));

		mTranslationText = (TextView) findViewById(R.id.translation_txt);
	}
	@Override
	protected void onResume()
	{
		super.onResume();

		restartLoader(getEditTextString(), true);
		mFromLanguage = TranslatorPrefs.getLastLanguage(this);
		setHeader(getString(R.string.add_word_reg, mFromLanguage.getDisplayName()));

	}

	@Override
	protected int getMenuId()
	{
		return R.menu.menu_add_word;
	}


	@Override
	protected void onRestoreInstanceState(final Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		mFromLanguage = (Language) savedInstanceState.getSerializable(sFromKey);
	}

	@Override
	public void onSaveInstanceState(final Bundle outState)
	{
		outState.putSerializable(sFromKey, mFromLanguage);
		super.onSaveInstanceState(outState);
	}


	@Override
	public void restartLoader(final String string, final boolean preTranslation)
	{
		getSupportLoaderManager().destroyLoader(TRANSLATE_LOADER_ID);
		getSupportLoaderManager().restartLoader(TRANSLATE_LOADER_ID, null, new TranslateLoaderCallBack(preTranslation, string, mFromLanguage))
				.forceLoad();
	}

	@Override
	int getHintResId()
	{
		return R.string.hint_translate;
	}

	public void onTranslationSuccess(final TranslationResult translationResult)
	{
		mTranslationText.setText(translationResult.getToText());
		if (!translationResult.isPreTranslation())
		{
			Toast.makeText(this, getString(R.string.added_word_reg, translationResult.getFromText()), Toast.LENGTH_SHORT).show();
		}

		toggleTranslationVisibility(true);
	}

	private void toggleTranslationVisibility(final boolean translationIsVisible)
	{
		findViewById(R.id.translation_cont).setVisibility(translationIsVisible ? View.VISIBLE : View.GONE);
		findViewById(R.id.error_cont).setVisibility(!translationIsVisible ? View.VISIBLE : View.GONE);
	}


	public void onTranslationFail(final Exception exception)
	{
		toggleTranslationVisibility(false);
		setError(ErrorProcessor.getError(exception, this));
	}

	@Override
	protected void onRetryButtonClick()
	{
		restartLoader(getEditTextString(),true);
	}


	private class TranslateLoaderCallBack implements LoaderManager.LoaderCallbacks<ComplexResult<TranslationResult>>
	{
		private final boolean mPreTranslation;
		private final String mText;
		private final Language mFromLanguage;


		public TranslateLoaderCallBack(final boolean preTranslation, final String text, final Language fromLanguage)
		{
			mPreTranslation = preTranslation;
			mText = text;
			mFromLanguage = fromLanguage;
		}

		@Override
		public Loader<ComplexResult<TranslationResult>> onCreateLoader(final int i, final Bundle bundle)
		{
			return new TranslateLoader(mPreTranslation, mText, mFromLanguage);
		}

		@Override
		public void onLoadFinished(final Loader<ComplexResult<TranslationResult>> loader, final ComplexResult<TranslationResult> result)
		{
			if (result.hasException())
			{
				onTranslationFail(result.getException());
			}
			else
			{
				onTranslationSuccess(result.getData());
			}
		}

		@Override
		public void onLoaderReset(final Loader<ComplexResult<TranslationResult>> loader)
		{

		}
	}

	@Override
	public boolean onMenuItemClick(final MenuItem menuItem)
	{
		if (menuItem.getItemId() == R.id.action_settings)
		{
			SelectLanguageActivity.startActivity(this);
			return true;
		}
		return false;
	}

	public static void startActivity(Context context)
	{
		context.startActivity(new Intent(context, AddWordActivity.class));
	}
}

