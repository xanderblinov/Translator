package com.xanderblinov.translator.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Date: 3/9/2015
 * Time: 11:43 AM
 *
 * @author xanderblinov
 */
public abstract class CursorIdAdapter extends BaseAdapter
{
	private Cursor mCursor;
	protected final Activity mActivity;
	private int mRowIDColumn;
	private String mIdColumn;

	public CursorIdAdapter(Activity activity, Cursor cursor)
	{
		mActivity = activity;

		init(cursor, "_id");
	}

	public CursorIdAdapter(Activity activity, Cursor cursor, String idColumn)
	{
		mActivity = activity;

		init(cursor, idColumn);
	}

	private void init(Cursor cursor, String idColumn)
	{
		if (cursor == null)
		{
			throw new NullPointerException("Cursor can not be null");
		}
		mCursor = cursor;

		mIdColumn = idColumn;

		if (TextUtils.isEmpty(mIdColumn))
		{
			mRowIDColumn = -1;
		}
		else
		{
			mRowIDColumn = cursor.getColumnIndex(mIdColumn);
		}

	}

	public Cursor getCursor()
	{
		return mCursor;
	}

	@Override
	public int getCount()
	{
		if (mCursor != null)
		{
			return mCursor.getCount();
		}

		return 0;
	}

	@Override
	public Cursor getItem(int position)
	{
		if (mCursor != null & mCursor.moveToPosition(position))
		{
			return mCursor;
		}

		return null;
	}

	@Override
	public long getItemId(int position)
	{
		if (mCursor != null & mRowIDColumn != -1 & mCursor.moveToPosition(position))
		{
			return mCursor.getLong(mRowIDColumn);
		}

		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (!mCursor.moveToPosition(position))
		{
			throw new IllegalStateException("Couldn't move cursor to position " + position);
		}

		View v;

		if (convertView == null)
		{
			v = initView(mActivity, mCursor, parent);
		}
		else
		{
			v = convertView;

			renitView(v, mActivity, mCursor);
		}

		bindView(v, mActivity, mCursor);

		return v;
	}

	protected abstract View initView(Activity activity, Cursor cursor, ViewGroup parent);

	protected abstract void bindView(View view, Activity activity, Cursor cursor);

	protected void renitView(View view, Context context, Cursor cursor)
	{

	}

	public void changeCursor(Cursor cursor)
	{
		final Cursor old = swapCursor(cursor);

		if (old != null)
		{
			old.close();
		}
	}

	public void changeCursor(Cursor cursor, String idColumn)
	{
		final Cursor old = swapCursor(cursor, idColumn);

		if (old != null)
		{
			old.close();
		}
	}

	public Cursor swapCursor(Cursor newCursor)
	{
		if (newCursor == mCursor)
		{
			return null;
		}

		if (newCursor == null)
		{
			throw new IllegalArgumentException("Cursor can not be null");
		}

		final Cursor oldCursor = mCursor;

		init(newCursor, mIdColumn);

		notifyDataSetChanged();

		return oldCursor;
	}

	public Cursor swapCursor(Cursor newCursor, String idColumn)
	{
		if (newCursor == mCursor)
		{
			return null;
		}

		if (newCursor == null)
		{
			throw new IllegalArgumentException("Cursor can not be null");
		}

		final Cursor oldCursor = mCursor;

		init(newCursor, idColumn);

		notifyDataSetChanged();

		return oldCursor;
	}

	public Context getActivity()
	{
		return mActivity;
	}
}
