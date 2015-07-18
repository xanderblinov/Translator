package com.xanderblinov.translator.network.exception;

/**
 * Date: 3/9/2015
 * Time: 8:32 PM
 *
 * @author xanderblinov
 */
public class NoNetworkException extends Exception
{
	public NoNetworkException()
	{
	}

	public NoNetworkException(final String detailMessage)
	{
		super(detailMessage);
	}

	public NoNetworkException(final String detailMessage, final Throwable throwable)
	{
		super(detailMessage, throwable);
	}

	public NoNetworkException(final Throwable throwable)
	{
		super(throwable);
	}
}
