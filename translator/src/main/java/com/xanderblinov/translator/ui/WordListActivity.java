package com.xanderblinov.translator.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.xanderblinov.translator.R;
import com.xanderblinov.translator.adapter.WordAdapter;
import com.xanderblinov.translator.async.ComplexResult;
import com.xanderblinov.translator.async.GetWordsLoader;
import com.xanderblinov.translator.network.ErrorProcessor;
import com.xanderblinov.translator.utils.TranslatorPrefs;

/**
 * Date: 3/9/2015
 * Time: 12:12 PM
 *
 * @author xanderblinov
 */
public class WordListActivity extends BaseWordActivity
{

	private static final int GET_WORDS_ID = 1;
	private WordAdapter mAdapter;
	private ListView mListView;

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_word_list);

		initViews();

		restartLoader("", false);
	}

	@Override
	protected int getMenuId()
	{
		return R.menu.menu_words_list;
	}

	@Override
	void restartLoader(final String string, final boolean save)
	{
		getSupportLoaderManager().restartLoader(GET_WORDS_ID, null, new GetWordsLoaderCallbacks(string)).forceLoad();
	}

	private void initViews()
	{
		mListView = (ListView) findViewById(R.id.words_list);
		setHeader(getString(R.string.word_list_reg, TranslatorPrefs.getLastLanguage(this).getDisplayName()));
	}

	@Override
	protected boolean showBackButton()
	{
		return false;
	}

	private void toggleTranslationVisibility(final boolean wordsIsVisible)
	{
		findViewById(R.id.words_list).setVisibility(wordsIsVisible ? View.VISIBLE : View.GONE);
		findViewById(R.id.error_cont).setVisibility(!wordsIsVisible ? View.VISIBLE : View.GONE);
	}

	@Override
	protected void onRetryButtonClick()
	{
		restartLoader(getEditTextString(), false);
	}

	private class GetWordsLoaderCallbacks implements LoaderManager.LoaderCallbacks<ComplexResult<Cursor>>
	{

		private final String mString;

		public GetWordsLoaderCallbacks(final String string)
		{

			mString = string;
		}

		@Override
		public Loader<ComplexResult<Cursor>> onCreateLoader(int i, Bundle bundle)
		{
			return new GetWordsLoader(mString);
		}

		@Override
		public void onLoadFinished(Loader<ComplexResult<Cursor>> loader, ComplexResult<Cursor> data)
		{
			if (data.hasException())
			{
				onWordsLoadError(data.getException());
				return;
			}

			onWordsLoadSuccess(data.getData());
		}

		@Override
		public void onLoaderReset(Loader<ComplexResult<Cursor>> loader)
		{
			// pass
		}
	}

	private void onWordsLoadSuccess(final Cursor cursor)
	{
		if (mAdapter == null)
		{
			mAdapter = new WordAdapter(this, cursor);
			mListView.setAdapter(mAdapter);
		}
		else
		{
			mAdapter.swapCursor(cursor);
		}
		boolean isEmpty = cursor.getCount() == 0;
		toggleTranslationVisibility(!isEmpty);

		if (isEmpty)
		{
			setError(getString(R.string.no_words_title), getString(R.string.no_words_message), false);
		}

	}

	private void onWordsLoadError(final Exception exception)
	{
		toggleTranslationVisibility(false);
		setError(ErrorProcessor.getError(exception, this));
	}

	@Override
	public boolean onMenuItemClick(final MenuItem menuItem)
	{
		switch (menuItem.getItemId())
		{
			case R.id.action_add_word:

				AddWordActivity.startActivity(this);
				return true;

			case R.id.action_settings:

				SelectLanguageActivity.startActivity(this);
				return true;

		}
		return false;

	}

	@Override
	int getHintResId()
	{
		return R.string.hint_search;
	}


	@Override
	protected void onResume()
	{
		super.onResume();

		restartLoader(getEditTextString(), false);
		setHeader(getString(R.string.word_list_reg, TranslatorPrefs.getLastLanguage(this).getDisplayName()));

	}

	public static void startActivity(Context context)
	{
		context.startActivity(new Intent(context, WordListActivity.class));
	}
}
