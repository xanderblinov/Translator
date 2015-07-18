package com.xanderblinov.translator.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xanderblinov.translator.app.TranslatorLog;
import com.xanderblinov.translator.data.Direction;
import com.xanderblinov.translator.data.Language;
import com.xanderblinov.translator.data.Word;

/**
 * Date: 3/7/2015
 * Time: 12:51 AM
 *
 * @author xanderblinov
 */
public class TranslatorDbHelper extends OrmLiteSqliteOpenHelper
{
	public static final String DB_NAME = "translator.db";
	public static final int DB_VERSION = 1;

	public TranslatorDbHelper(final Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase sqLiteDatabase, final ConnectionSource connectionSource)
	{
		try
		{
			TableUtils.createTable(connectionSource, Language.class);
			TableUtils.createTable(connectionSource, Direction.class);
			TableUtils.createTable(connectionSource, Word.class);
		}
		catch (SQLException e)
		{
			TranslatorLog.log(e);
		}
	}

	@Override
	public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final ConnectionSource connectionSource, final int i, final int i1)
	{

	}
}
