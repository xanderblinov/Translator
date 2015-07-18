package com.xanderblinov.translator.ui;

import android.os.Bundle;
import android.view.View;

import com.xanderblinov.translator.R;
import com.xanderblinov.translator.data.GetLanguagesResult;
import com.xanderblinov.translator.network.ErrorProcessor;

/**
 * Date: 3/7/2015
 * Time: 2:43 PM
 *
 * @author xanderblinov
 */


public class SplashActivity extends AbsLoadLanguages implements View.OnClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_splash);
		initDisclaimer();
	}


	@Override
	protected void onLanguagesLoadSuccess(final GetLanguagesResult result)
	{
		startMainActivity();
	}


	private void toggleErrorContVisibility(final boolean show)
	{
		findViewById(R.id.error_cont).setVisibility(show ? View.VISIBLE : View.GONE);
	}

	@Override
	protected void onLanguagesLoadError(final Exception exception)
	{
		setError(ErrorProcessor.getError(exception, this));
		toggleErrorContVisibility(true);
	}

	private void startMainActivity()
	{
		WordListActivity.startActivity(this);
		finish();
	}

	@Override
	protected void onRetryButtonClick()
	{

	}
}

