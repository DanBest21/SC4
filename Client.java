import java.io.*;
import java.net.*;
import java.util.*;

public class Client 
{
	public static void main(String[] args) 
	{
		String hostname = "localhost";
		Integer portNumber = 2066;
		
		Socket socket = null;
		
		try
		{
			System.out.println("Connecting to chat server at " + hostname + " on port number " + portNumber);
			socket = new Socket(hostname, portNumber);
			System.out.println("Connected to server at " + socket.getRemoteSocketAddress());
			
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while (socket.isConnected())
			{
				Scanner input = new Scanner(System.in);
				String message = input.nextLine();
				
				output.write(message);
				output.flush();
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
}
