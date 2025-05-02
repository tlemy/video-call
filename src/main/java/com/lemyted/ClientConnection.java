package com.lemyted;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Thread
{
	private Socket sock;
	
	public boolean isRunning()
	{
		return running;
	}

	public void setRunning(boolean running)
	{
		this.running = running;
	}

	private boolean running;
	
	public ClientConnection(Socket sock)
	{
		this.sock = sock;
		this.running = true;
	}
	
	public void run()
	{
		String msg = String.format("New client: %s:%s", sock.getLocalAddress(), sock.getLocalPort());
		
		// TODO Add logs
		System.out.println(msg);
		
		while (running)
		{
			System.out.println("hello");
		}
		closeSocket();
	}
	
	private void closeSocket()
	{
		String msg = String.format("Delete client: %s:%s", sock.getLocalAddress(), sock.getLocalPort());
		
		// TODO Add logs
		System.out.println(msg);
		
		try
		{
			sock.close();
		} 
		catch (IOException e)
		{
			// TODO Add logs
			e.printStackTrace();
		}
	}
	
	public boolean isOpen()
	{
		return this.isAlive() && !sock.isClosed();
	}
}
