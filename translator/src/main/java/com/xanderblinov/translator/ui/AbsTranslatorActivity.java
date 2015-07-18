package com.xanderblinov.translator.ui;

import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.xanderblinov.translator.R;
import com.xanderblinov.translator.data.TranslatorError;

/**
 * Date: 3/9/2015
 * Time: 8:08 PM
 *
 * @author xanderblinov
 */
public abstract class AbsTranslatorActivity extends FragmentActivity implements View.OnClickListener
{

	protected void initDisclaimer()
	{
		TextView textView = (TextView) findViewById(R.id.yandex_disclaimer_txt);
		textView.setText(Html.fromHtml(getString(R.string.yandex_disclaimer)));
		textView.setMovementMethod(LinkMovementMethod.getInstance());
	}

	protected void setError(final String title, final String description)
	{
		setError(title,description,true);
	}

	protected void setError(final String title, final String description, boolean canRetry)
	{
		((TextView)findViewById(R.id.error_title_txt)).setText(title);
		((TextView)findViewById(R.id.error_description_txt)).setText(description);
		(findViewById(R.id.retry_btn)).setOnClickListener(this);
		(findViewById(R.id.retry_btn)).setVisibility(canRetry ? View.VISIBLE : View.GONE);
	}


	protected void setHeader(final String string)
	{
		TextView textView = ((TextView) findViewById(R.id.header_txt));
		if (textView != null)
		{
			textView.setText(string);
		}
	}
	protected void setError(final TranslatorError error)
	{
	 	setError(error.getTitle(),error.getMessage());
	}

	@Override
	public void onClick(final View v)
	{
		if(v.getId() == R.id.retry_btn){
			onRetryButtonClick();
		}
	}

	protected abstract void onRetryButtonClick();
}
