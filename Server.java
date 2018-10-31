import java.io.*;
import java.net.*;

public class Server extends Thread
{
	private static Integer portNumber = 2066;
	private static Integer timeout = 20000;
	private static ServerSocket serverSocket;
	
	public Server()
	{
		try
		{
			serverSocket = new ServerSocket(portNumber);
			serverSocket.setSoTimeout(timeout);
		}
		catch (IOException ex)
		{
			System.err.println(ex.getCause() + ": " + ex.getMessage());
		}
	}
	
	public void run()
	{
		Socket socket = null;
			
		try
		{
			System.out.println("Server started on port number " + serverSocket.getLocalPort());
				
			while (true)
			{
				socket = serverSocket.accept();
				
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
				System.out.println(input.readLine());
			}
		}
		catch (IOException ex)
		{
			System.err.println(ex.getCause() + ": " + ex.getMessage());
		}
		finally 
		{
			try
			{
				socket.close();
			}
			catch (IOException ex)
			{
				System.err.println(ex.getCause() + ": " + ex.getMessage());
			}
		}
	}
	
	public static void main(String [] args)
	{
		try
		{
			Thread thread = new Server();
			thread.start();
		}
		catch (Exception ex)
		{
			System.err.println(ex.getCause() + ": " + ex.getMessage());
		}
	}
}
