package com.xanderblinov.translator.data;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Date: 3/7/2015
 * Time: 12:55 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = Language.TABLE_NAME)
public class Language implements Serializable
{
	public static final String TABLE_NAME = "language";

	public static class Column
	{
		public static final String NAME = "name";
		public static final String DISPLAY_NAME = "display_name";
	}

	@DatabaseField(columnName = Column.NAME, id = true)
	private String mName;

	@DatabaseField(columnName = Column.DISPLAY_NAME)
	private String mDisplayName;

	public Language()
	{
		//for orm lite
	}

	public Language(final String name, final String displayName)
	{
		mName = name;
		mDisplayName = displayName;
	}

	public String getName()
	{
		return mName;
	}

	public String getDisplayName()
	{
		return mDisplayName;
	}
}
