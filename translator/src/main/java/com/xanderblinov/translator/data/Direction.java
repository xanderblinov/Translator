package com.xanderblinov.translator.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Date: 3/7/2015
 * Time: 12:55 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = Direction.TABLE_NAME)
public class Direction
{
	public static final String TABLE_NAME = "direction";

	public static class Column
	{
		public static final String ID = "_id";
		public static final String FROM = "from";
		public static final String TO = "to";
	}

	@DatabaseField(columnName = Column.ID, generatedId = true)
	private long mId;

	@DatabaseField(columnName = Column.FROM)
	private String mFrom;

	@DatabaseField(columnName = Column.TO)
	private String mTo;

	public Direction(final String from, final String to)
	{
		mFrom = from;
		mTo = to;
	}

	public Direction()
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
