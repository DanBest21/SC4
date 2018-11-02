import java.io.*;
import java.net.*;

// Class that handles the server-side of processing for each individual client connection.
public class ServerThread implements Runnable
{
	// Instance variables
	private Server server;
	private Socket socket;
	
	// Constructor that sets the server and socket variables to the parameters passed from the Server class.
	public ServerThread(Server serverInfo, Socket clientSocket)
	{
		server = serverInfo;
		socket = clientSocket;
	}
	
	// Interface method from Runnable that is ran as a thread concurrently, and handles each client that is connected to the server.
	public void run()
	{	
		try
		{		
			// Create a new PrintWriter and add it to the list that the Server class has so that the sendMessage() method works correctly.
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			server.addWriter(writer);
			
			// Create a BufferedReader object to receive messages from the client.
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String receivedMessage;
			
			// Infinite loop that awaits messages from the client, and handles them accordingly.
			while (true)
			{			
				receivedMessage = input.readLine();
				
				// If the receivedMessage contains the phrase "SET USERNAME", then set the user name of this user to the String provided.
				if (receivedMessage.contains("SET USERNAME"))
				{
					String username = receivedMessage.substring(receivedMessage.indexOf("SET USERNAME") + ("SET USERNAME").length() + 1);
					server.updateUserList(socket, username);
					System.out.println("User " + username + " has connected.");
					server.sendMessage("User " + username + " has connected.", writer);
				}
				// Otherwise, just print out the message and send it back to the client, formatting it in such a way that the user name of the user who sent it is put before the message.
				else
				{
					System.out.println(String.format("[%s] " + receivedMessage, server.getUsername(socket)));
					server.sendMessage(String.format("[%s] " + receivedMessage, server.getUsername(socket)), writer);
				}
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
