package com.xanderblinov.translator.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xanderblinov.translator.R;
import com.xanderblinov.translator.data.Word;

/**
 * Date: 3/9/2015
 * Time: 11:47 AM
 *
 * @author xanderblinov
 */
public class WordAdapter extends CursorIdAdapter
{

	private final int mResId;

	public WordAdapter(final Activity activity, final Cursor cursor)
	{
		super(activity, cursor);
		mResId = R.layout.item_word;
	}

	@Override
	protected View initView(final Activity activity, final Cursor cursor, final ViewGroup parent)
	{
		View view = null;

		if (mActivity != null)
		{
			view = mActivity.getLayoutInflater().inflate(mResId, parent, false);
		}
		else
		{
			return null;
		}
		Holder holder = new Holder(view);
		view.setTag(holder);
		return view;
	}

	@Override
	protected void bindView(final View view, final Activity activity, final Cursor cursor)
	{
		if (view == null||view.getTag() == null)
		{
			return;
		}

		Holder holder = (Holder) view.getTag();
		holder.getFromText().setText(cursor.getString(cursor.getColumnIndexOrThrow(Word.Column.WORD)));
		holder.getToText().setText(cursor.getString(cursor.getColumnIndexOrThrow(Word.Column.TRANSLATION)));
	}

	public static class Holder
	{
		private final TextView mFromText;
		private final TextView mToText;

		public Holder(final View root)
		{
			mFromText = (TextView) root.findViewById(R.id.from_txt);
			mToText = (TextView) root.findViewById(R.id.to_txt);
		}

		public TextView getFromText()
		{
			return mFromText;
		}

		public TextView getToText()
		{
			return mToText;
		}
	}
}
