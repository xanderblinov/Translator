package com.xanderblinov.translator.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import android.database.Cursor;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.xanderblinov.translator.app.TranslatorApp;
import com.xanderblinov.translator.data.Direction;
import com.xanderblinov.translator.data.Language;
import com.xanderblinov.translator.data.Word;

/**
 * Date: 3/7/2015
 * Time: 1:15 PM
 *
 * @author xanderblinov
 */
public class DbClient
{
	private TranslatorApp mTranslatorApp;

	public DbClient(final TranslatorApp translatorApp)
	{
		mTranslatorApp = translatorApp;
		getDBHelper();
	}

	public OrmLiteSqliteOpenHelper getDBHelper()
	{
		return OpenHelperManager.getHelper(mTranslatorApp, TranslatorDbHelper.class);
	}

	public void saveLanguages(final List<Language> languages, final List<Direction> directions) throws Exception
	{
		final Dao<Language, String> languageDao = getDBHelper().getDao(Language.class);
		final Dao<Direction, Long> directionDao = getDBHelper().getDao(Direction.class);


		languageDao.callBatchTasks(new Callable<Void>()
		{
			@Override
			public Void call() throws Exception
			{
				for (Language language : languages)
				{
					languageDao.createOrUpdate(language);
				}
				return null;
			}
		});

		directionDao.callBatchTasks(new Callable<Void>()
		{
			@Override
			public Void call() throws Exception
			{
				TableUtils.clearTable(getDBHelper().getConnectionSource(), Direction.class);

				for (Direction direction : directions)
				{
					directionDao.create(direction);
				}
				return null;
			}
		});
	}

	public List<Language> getLanguages() throws SQLException
	{
		final Dao<Language, String> languageDao = getDBHelper().getDao(Language.class);

		return languageDao.queryForAll();
	}

	public List<Language> getLanguageDirections(final Language language, final boolean from) throws Exception
	{
		final Dao<Language, String> languageDao = getDBHelper().getDao(Language.class);
		final Dao<Direction, Long> directionDao = getDBHelper().getDao(Direction.class);

		final List<Direction> directions = directionDao.queryForEq(from ? Direction.Column.FROM : Direction.Column.TO, language.getName());

		final List<Language> languages = new ArrayList<>();
		languageDao.callBatchTasks(new Callable<Void>()
		{
			@Override
			public Void call() throws Exception
			{
				Language temp;
				for (Direction direction : directions)
				{
					//@formatter:off
					temp = languageDao.queryForFirst(languageDao
							.queryBuilder()
							.where()
							.eq(Language.Column.NAME, from ? direction.getTo() : direction.getFrom())
							.prepare());
					//@formatter:on

					if (temp != null)
					{
						languages.add(temp);
					}
				}
				return null;
			}
		});

		return languages;
	}

	public void saveWord(final String toLang, final String textFrom, final String textTo) throws SQLException
	{
		final Dao<Word, String> wordDao = getDBHelper().getDao(Word.class);

		final Word word = wordDao.queryForFirst(
				wordDao.queryBuilder().where().eq(Word.Column.WORD, textFrom).and().eq(Word.Column.LANGUAGE, toLang).prepare());
		if (word == null)
		{
			wordDao.create(new Word(textFrom, textTo, toLang));
		}
	}

	public Language getLanguage(final String lastLanguage) throws SQLException
	{
		final Dao<Language, String> languageDao = getDBHelper().getDao(Language.class);

		List<Language> languages = languageDao.queryForEq(Language.Column.NAME, lastLanguage);

		if (languages.isEmpty())
		{
			return null;
		}

		return languages.get(0);
	}

	public Cursor getWords(final Language fromLang, final String string) throws SQLException
	{
		String query;
		final String search = "%" + string + "%";
		//@formatter:off
		query = "SELECT *"
				+ " FROM " + Word.TABLE_NAME
				+ " WHERE " + Word.Column.LANGUAGE + "=" + "\'" + fromLang.getName() + "\'"
				+ " AND " + "("
				+ Word.Column.WORD + " like " + "?"
				+ " OR "
				+ Word.Column.TRANSLATION + " like " + "?"
				+ ")"
				+ " ORDER BY " +Word.Column.ID +" DESC";

		//@formatter:on

		return getDBHelper().getReadableDatabase().rawQuery(query, new String[]{
				search,
				search
		});
	}

	public List<Language> getSupportLanguages(final String languageString) throws Exception
	{
		final Dao<Language, String> languageDao = getDBHelper().getDao(Language.class);
		Dao<Direction, Integer> directionDao = getDBHelper().getDao(Direction.class);

		Language language = languageDao.queryForFirst(languageDao.queryBuilder().where().eq(Language.Column.NAME, languageString).prepare());

		if (language == null)
		{
			return new ArrayList<>();
		}
		final List<Direction> directions =
				directionDao.query(directionDao.queryBuilder().where().eq(Direction.Column.TO, language.getName()).prepare());
		final List<Language> languages = new ArrayList<>();

		languageDao.callBatchTasks(new Callable<Void>()
		{
			@Override
			public Void call() throws Exception
			{
				Language languageTemp;
				for (Direction direction : directions)
				{
					languageTemp =
							languageDao.queryForFirst(languageDao.queryBuilder().where().eq(Language.Column.NAME, direction.getFrom()).prepare());
					if (languageTemp != null)
					{
						languages.add(languageTemp);
					}
				}
				return null;
			}
		});
		Collections.sort(languages, new Comparator<Language>()
		{
			@Override
			public int compare(final Language lhs, final Language rhs)
			{
				return lhs.getDisplayName().compareToIgnoreCase(rhs.getDisplayName());
			}
		});
		return languages;
	}

	public boolean isDeployed()
	{
		try
		{
			final Dao<Language, String> languageDao = getDBHelper().getDao(Language.class);
			final Dao<Direction, Long> directionDao = getDBHelper().getDao(Direction.class);
			return !(directionDao.queryForAll().isEmpty() || languageDao.queryForAll().isEmpty());
		}
		catch (SQLException e)
		{
			return false;
		}
	}
}
