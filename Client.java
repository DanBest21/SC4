import java.io.*;
import java.net.*;
import java.util.*;

// Class that handles the client-side processing of the application.
public class Client 
{
	// Instance variables
	private String hostname;
	private Integer portNumber;	
	private Socket socket = null;
	
	// Setter method that prompts the user to enter a user name.
	public String setUsername(Scanner in)
	{
		System.out.println("Please enter a username:");
		return "SET USERNAME " + in.nextLine();
	}
	
	// Constructor method that sets the hostname and portNumber variables.
	public Client()
	{
		hostname = "localhost";
		portNumber = 2066;
	}
	
	// Core function that launches the client in the console window and handles it going forward.
	public void launchClient()
	{
		try
		{
			// Connect to the server.
			System.out.println("Connecting to chat server at " + hostname + " on port number " + portNumber);
			socket = new Socket(hostname, portNumber);
			System.out.println("Connected to server at " + socket.getRemoteSocketAddress());
			
			// Instantiate the PrintWriter and BufferedReader objects that will act as the input and output collectors.
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// Create a scanner object to allow the user to use the console.
			Scanner input = new Scanner(System.in);
			
			// Set the user name and pass it to the server to be remembered.
			String username = setUsername(input);
			output.println(username);
			
			// Create a new ClientListener thread to output any messages.
			Thread thread = new Thread(new ClientListener(serverInput));
			thread.start();
			
			// Infinite loop that waits for the user to input a message, and then sends it to the server to be handled.
			while (true)
			{
				String message = input.nextLine();
				output.println(message);
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
	
	// Main method that instantiates the Client class and then calls the core launchClient() function.
	public static void main(String[] args) 
	{
		Client client = new Client();
		client.launchClient();
	}
}
