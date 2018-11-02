import java.io.*;
import java.net.*;
import java.util.*;

// Class that handles the server-side of the chat application.
public class Server
{
	// Instance variables
	private static Integer portNumber = 2066;
	private static ServerSocket serverSocket;
	
	private static HashMap<Socket, String> userList = new HashMap<Socket, String>();
	private static HashSet<PrintWriter> printWriters = new HashSet<PrintWriter>();
	
	// Constructor that instantiates the ServerSocket object that Socket objects will be created from.
	public Server()
	{
		try
		{
			serverSocket = new ServerSocket(portNumber);
		}
		catch (IOException ex)
		{
			System.err.println(ex.getCause() + ": " + ex.getMessage());
		}
	}
	
	// Getter method that gets the user name associated to the Socket object passed.
	public String getUsername(Socket socket)
	{
		return userList.get(socket);
	}
	
	// Function that updates the userList HashMap, in order to associate a user name to each new client that connects to the server.
	public void updateUserList(Socket socket, String username)
	{
		userList.put(socket, username);
	}
	
	// Function that adds a PrintWriter attached to a client to the printWriters HashSet.
	public void addWriter(PrintWriter writer)
	{
		printWriters.add(writer);
	}
	
	// Method that sends each received message to every client except the one that sent/caused it.
	public void sendMessage(String message, PrintWriter clientToIgnore)
	{
		// Loop through each print writer.
		for (PrintWriter output : printWriters)
		{
			// Send the message unless this is the client that sent it.
			if (output != clientToIgnore)
			{
				output.println(message);
			}
		}
	}
	
	// Main method that instantiates the server and creates new ServerThread objects every time a new client connects.
	public static void main(String [] args)
	{
		try
		{			
			Server server = new Server();
			System.out.println("Server started on port number " + serverSocket.getLocalPort());
			
			// An infinite loop that will handle any new client that attempts to connect to the server.
			while (true)
			{
				// Accept the connection, create a socket, and create a new ServerThread object to specifically handle that user.
				Socket socket = serverSocket.accept();
				Thread thread = new Thread(new ServerThread(server, socket));
				thread.start();
			}
		}
		catch (Exception ex)
		{
			System.err.println(ex.getCause() + ": " + ex.getMessage());
		}
	}
}
