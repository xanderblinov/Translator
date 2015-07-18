package com.xanderblinov.translator.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xanderblinov.translator.R;
import com.xanderblinov.translator.data.Language;

/**
 * Date: 3/9/2015
 * Time: 3:05 PM
 *
 * @author xanderblinov
 */
public class LanguageAdapter extends ArrayAdapter<Language>
{
	private Language mDefault;

	public LanguageAdapter(final Context context, final List<Language> objects)
	{
		super(context, R.layout.item_language,R.id.language_txt, objects);
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent)
	{
		View view = super.getView(position, convertView, parent);

		Holder holder;
		if (view.getTag() != null)
		{
			holder = (Holder) view.getTag();
		}
		else
		{
			holder = new Holder(view);
		}

		Language language = getItem(position);
		holder.getFromText().setText(language.getDisplayName());

		view.setTag(holder);

		view.setBackgroundResource(mDefault.getName().equals(language.getName()) ? R.drawable.border_primary_fill : R.drawable.item_language);

		return view;
	}

	public void setDefault(final Language aDefault)
	{
		mDefault = aDefault;
	}

	public static class Holder
	{
		private final TextView mFromText;
		private final TextView mToText;

		public Holder(final View root)
		{
			mFromText = (TextView) root.findViewById(R.id.language_txt);
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
