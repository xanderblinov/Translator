package com.xanderblinov.translator.network.exception;

/**
 * Date: 3/9/2015
 * Time: 8:37 PM
 *
 * @author xanderblinov
 */
public class ServerErrorException extends Exception
{
	public ServerErrorException()
	{
	}

	public ServerErrorException(final String detailMessage)
	{
		super(detailMessage);
	}

	public ServerErrorException(final String detailMessage, final Throwable throwable)
	{
		super(detailMessage, throwable);
	}

	public ServerErrorException(final Throwable throwable)
	{
		super(throwable);
	}
}
