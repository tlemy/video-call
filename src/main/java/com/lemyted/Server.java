package com.lemyted;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.util.LinkedList;
import java.util.List;

public class Server
{
	static final int PORT = 8800;
	
	static final int BACKLOG = 5;
	
	private ServerSocketChannel channel;
	
	private ServerSocket servSock;
	
	private List<ClientConnection> connections;
		
	public Server()
	{
		try
		{
			channel = ServerSocketChannel.open();
			channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			channel.bind(new InetSocketAddress(PORT), BACKLOG);
			
			setServSock(channel.socket());

			if (!servSock.isBound())
			{
				throw new IOException("Socket not bound");
			}
			
			System.out.println("Port: " + servSock.getLocalPort());
		} 
		catch (IOException e)
		{
			// TODO Add logs
			e.printStackTrace();
			closeServerSocket();
		}
		connections = new LinkedList<ClientConnection>();
	}
	
	public void run()
	{
		try
		{			
			while (true)
			{
				ClientConnection conn = new ClientConnection(servSock.accept());
				
				connections.add(conn);
				
				conn.run();
			}
		}
		catch (IOException e) {
			// TODO Add logs
			e.printStackTrace();
		}
		closeServerSocket();
		closeConnections();
	}
	
	private void closeServerSocket()
	{
		if (servSock != null && !servSock.isClosed())
		{
			try
			{
				servSock.close();
			} 
			catch (IOException e)
			{
				// TODO Add logs
				e.printStackTrace();
			}
		}
	}
	
	private void closeConnections()
	{
		for (ClientConnection conn : connections)
		{
			if (conn.isOpen())
			{
				conn.setRunning(false);
			}
		}
	}

	public ServerSocket getServSock()
	{
		return servSock;
	}

	private void setServSock(ServerSocket servSock)
	{
		this.servSock = servSock;
	}
}
	