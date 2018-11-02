import java.io.*;

public class ClientListener implements Runnable
{
	// Instance variables
	private BufferedReader input;
	
	// Constructor method that instantiates the BufferedReader input variable.
	public ClientListener(BufferedReader serverInput)
	{
		input = serverInput;
	}
	
	// Interface method from Runnable that is ran as a thread concurrently, and prints messages to the client when received from the server.
	public void run()
	{
		try
		{
			while (true)
			{
				System.out.println(input.readLine());
			}
		}
		catch (Exception ex)
		{
			System.err.println(ex.getCause() + ": " + ex.getMessage());
		}
	}
}
