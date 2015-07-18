package com.xanderblinov.translator.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xanderblinov.translator.R;
import com.xanderblinov.translator.adapter.LanguageAdapter;
import com.xanderblinov.translator.data.GetLanguagesResult;
import com.xanderblinov.translator.network.ErrorProcessor;
import com.xanderblinov.translator.utils.TranslatorPrefs;

/**
 * Date: 3/9/2015
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */
public class SelectLanguageActivity extends AbsLoadLanguages implements AdapterView.OnItemClickListener
{
	private LanguageAdapter mAdapter;
	private ListView mListView;

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_select_language);

		initViews();

		initDisclaimer();
	}

	private void initViews()
	{
		mListView = ((ListView) findViewById(R.id.languages_list));
		mListView.setOnItemClickListener(this);
		setHeader(getString(R.string.select_langugage_header));
		findViewById(R.id.back_img).setOnClickListener(this);
	}


	@Override
	protected void onLanguagesLoadSuccess(final GetLanguagesResult result)
	{
		if (mAdapter == null)
		{
			mAdapter = new LanguageAdapter(this, result.getLanguages());
			mAdapter.setDefault(TranslatorPrefs.getLastLanguage(this));
			mListView.setAdapter(mAdapter);

		}
		else
		{
			mAdapter.clear();
			mAdapter.addAll(result.getLanguages());
			mAdapter.notifyDataSetChanged();
			mAdapter.setDefault(TranslatorPrefs.getLastLanguage(this));
		}

		toggleTranslationVisibility(result.getLanguages().size() > 0);
	}

	@Override
	protected void onLanguagesLoadError(final Exception exception)
	{
		toggleTranslationVisibility(false);
		setError(ErrorProcessor.getError(exception, this));
	}


	private void toggleTranslationVisibility(final boolean languageListIsVisible)
	{
		findViewById(R.id.languages_list).setVisibility(languageListIsVisible ? View.VISIBLE : View.GONE);
		findViewById(R.id.error_cont).setVisibility(!languageListIsVisible ? View.VISIBLE : View.GONE);
	}

	public static void startActivity(Context context)
	{
		context.startActivity(new Intent(context, SelectLanguageActivity.class));
	}

	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id)
	{
		if (mAdapter == null)
		{
			return;
		}
		TranslatorPrefs.setLastLanguage(this, mAdapter.getItem(position));
		finish();
	}

	@Override
	protected void onRetryButtonClick()
	{
		restartLoader();
	}

	@Override
	public void onClick(final View v)
	{
		super.onClick(v);

		switch (v.getId())
		{
			case R.id.back_img:
				super.onBackPressed();
				break;
		}
	}
}
