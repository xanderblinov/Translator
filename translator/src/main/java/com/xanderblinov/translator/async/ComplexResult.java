package com.xanderblinov.translator.async;

/**
 * Date: 3/7/2015
 * Time: 10:36 AM
 *
 * @author xanderblinov
 */
public class ComplexResult<Result>
{
	private Exception mException;
	private Result mData;

	/**
	 * @param data Result of loader
	 */
	public ComplexResult(Result data)
	{
		mData = data;
	}

	/**
	 * @param error which happened during background work
	 */
	public ComplexResult(Exception error)
	{
		mException = error;
	}

	/**
	 * @return exception which happened during background work. or null if work
	 * was successful.
	 */
	public Exception getException()
	{
		return mException;
	}

	/**
	 * @param error exception which happened during background work.
	 */
	public void setException(Exception error)
	{
		mException = error;
	}

	public boolean hasException()
	{
		return mException != null;
	}

	/**
	 * @return result of loader if was no exceptions
	 */
	public Result getData()
	{
		return mData;
	}

	/**
	 * @param data result of loader if was no exceptions
	 */
	public void setData(Result data)
	{
		mData = data;
	}
}
