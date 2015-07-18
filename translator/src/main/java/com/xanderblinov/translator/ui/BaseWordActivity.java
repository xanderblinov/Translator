package com.xanderblinov.translator.ui;

import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.support.v7.widget.ActionMenuView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xanderblinov.translator.R;

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


public abstract class BaseWordActivity extends AbsTranslatorActivity
		implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener, ActionMenuView.OnMenuItemClickListener
{
	private EditText mEditText;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		super.setContentView(R.layout.act_base_word);

		initDisclaimer();

		initActionMenu();

		initBaseViews();
	}


	@Override
	public void setContentView(int layoutResID)
	{
		final ViewGroup content = (ViewGroup) findViewById(R.id.base_word_cont);
		final View view = LayoutInflater.from(this).inflate(layoutResID, content, false);
		final FrameLayout.LayoutParams params =
				new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		content.addView(view);
	}

	private void initBaseViews()
	{
		findViewById(R.id.back_img).setOnClickListener(this);
		findViewById(R.id.back_img).setVisibility(showBackButton() ? View.VISIBLE : View.GONE);

		findViewById(R.id.remove_btn).setOnClickListener(this);

		mEditText = (EditText) findViewById(R.id.text_edit);
		mEditText.setHint(getHintResId());
		mEditText.addTextChangedListener(this);
		mEditText.setOnEditorActionListener(this);
	}

	protected boolean showBackButton()
	{
		return true;
	}

	private void initActionMenu()
	{
		ActionMenuView actionMenuView = (ActionMenuView) findViewById(R.id.view_menu);
		new SupportMenuInflater(this).inflate(getMenuId(), actionMenuView.getMenu());
		actionMenuView.setOnMenuItemClickListener(this);
	}

	protected abstract int getMenuId();

	@Override
	public void onClick(final View v)
	{

		super.onClick(v);
		switch (v.getId())
		{
			case R.id.back_img:
				super.onBackPressed();
				break;
			case R.id.remove_btn:
				removeText();
				break;
		}
	}

	private void removeText()
	{
		mEditText.setText("");
	}


	@Override
	public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after)
	{

	}

	@Override
	public void onTextChanged(final CharSequence s, final int start, final int before, final int count)
	{

	}

	@Override
	public void afterTextChanged(final Editable s)
	{
		restartLoader(s.toString(), true);
	}

	abstract void restartLoader(final String string, final boolean save);

	@Override
	public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event)
	{
		boolean handled = false;
		if (actionId == EditorInfo.IME_ACTION_DONE)
		{
			restartLoader(mEditText.getText().toString(), false);
			handled = true;
		}
		return handled;
	}

	abstract int getHintResId();

	protected String getEditTextString()
	{
		if (mEditText == null || TextUtils.isEmpty(mEditText.getText()))
		{
			return "";
		}
		return mEditText.getText().toString();
	}
}

