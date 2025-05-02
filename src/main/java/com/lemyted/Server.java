package com.lemyted;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.List;

import javax.net.ServerSocketFactory;

public class Server {
	static final int PORT = 8800;
	
	static final int BACKLOG = 5;
	
	private ServerSocket servSock;
	
	private List<Socket> connections;
		
	public Server() {
		try {
			setServSock(ServerSocketFactory.getDefault().createServerSocket(PORT));
			
			if (!servSock.isBound())
			{
				throw new IOException("Socket not bound");
			}
			
			System.out.println("Port: " + servSock.getLocalPort());
		} 
		catch (IOException e) {
			// TODO Add logs
			e.printStackTrace();
			closeServerSocket();
		}
		connections = new LinkedList<Socket>();
	}
	
	public void run() {
		try {			
			while (true)
			{
				connections.add(servSock.accept());
				System.out.println("added new connection");
			}
		} catch (IOException e) {
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
			try {
				servSock.close();
			} catch (IOException e) {
				// TODO Add logs
				e.printStackTrace();
			}
		}
	}
	
	private void closeConnections()
	{
		for (Socket sock : connections)
		{
			if (!sock.isClosed())
			{
				try {
					sock.close();
				} catch (IOException e) {
					// TODO Add logs
					e.printStackTrace();
				}
			}
		}
	}

	public ServerSocket getServSock() {
		return servSock;
	}

	private void setServSock(ServerSocket servSock) {
		this.servSock = servSock;
	}
}
	