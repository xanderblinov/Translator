package com.xanderblinov.translator.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Date: 3/8/2015
 * Time: 11:20 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = Word.TABLE_NAME)
public class Word
{
	public static final String TABLE_NAME = "word";

	public static class Column
	{
		public static final String ID = "_id";
		public static final String WORD = "word";
		public static final String TRANSLATION = "translation";
		public static final String LANGUAGE = "language";
	}

	@DatabaseField(columnName = Column.ID, generatedId = true)
	private long mId;

	@DatabaseField(columnName = Column.WORD)
	private String mFrom;

	@DatabaseField(columnName = Column.TRANSLATION)
	private String mTo;

	@DatabaseField(columnName = Column.LANGUAGE)
	private String mLanguage;

	public Word(final String from, final String to, final String language)
	{
		mFrom = from;
		mTo = to;
		mLanguage = language;
	}

	public Word()
	{
		//for orm lite
	}

	public String getFrom()
	{
		return mFrom;
	}

	public void setFrom(final String from)
	{
		mFrom = from;
	}

	public String getTo()
	{
		return mTo;
	}

	public void setTo(final String to)
	{
		mTo = to;
	}
}
