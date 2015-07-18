package com.xanderblinov.translator.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.xanderblinov.translator.async.ComplexResult;
import com.xanderblinov.translator.async.GetLanguagesLoader;
import com.xanderblinov.translator.data.GetLanguagesResult;

/**
 * Date: 3/7/2015
 * Time: 2:43 PM
 *
 * @author xanderblinov
 */

public abstract class AbsLoadLanguages extends AbsTranslatorActivity
{
	protected static final int GET_LANGUAGES_LOADER_ID = 1;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		restartLoader();
	}

	protected void restartLoader()
	{
		getSupportLoaderManager().restartLoader(GET_LANGUAGES_LOADER_ID, null, new GetLanguagesLoaderCallbacks()).forceLoad();
	}


	private class GetLanguagesLoaderCallbacks implements LoaderManager.LoaderCallbacks<ComplexResult<GetLanguagesResult>>
	{
		@Override
		public Loader<ComplexResult<GetLanguagesResult>> onCreateLoader(int i, Bundle bundle)
		{
			return new GetLanguagesLoader();
		}

		@Override
		public void onLoadFinished(Loader<ComplexResult<GetLanguagesResult>> loader, ComplexResult<GetLanguagesResult> data)
		{
			if (data.hasException())
			{
				onLanguagesLoadError(data.getException());
				return;
			}

			onLanguagesLoadSuccess(data.getData());
		}

		@Override
		public void onLoaderReset(Loader<ComplexResult<GetLanguagesResult>> loader)
		{
			// pass
		}
	}

	protected abstract void onLanguagesLoadSuccess(final GetLanguagesResult result);

	protected abstract void onLanguagesLoadError(final Exception exception);
}

