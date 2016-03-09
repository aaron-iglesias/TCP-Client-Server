import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

	//private static String PORT = "9151";

	public static void main(String[] args) {
		Socket socket = null;
		PrintStream outputStream = null;
		BufferedReader inputStream = null;
		String serverURL = args[0];
		int port = Integer.parseInt(args[1]);

		try {
			socket = new Socket(serverURL, port);
			// socket = new Socket("127.0.0.1",9151);
			// create input and output streams to communicate with server
			outputStream = new PrintStream(socket.getOutputStream());
			inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverURL);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverURL);
        }
        try {

	        while(true) {
	        	// prompt user to input operation to send to server
		        Scanner scanner = new Scanner(System.in);
		        //System.out.println("operation: ");
		        String input = scanner.nextLine();
		        outputStream.println(input);
		        // get server's response
		        String response = inputStream.readLine();
		        // if server's response is negative, get error message
		        if(Integer.parseInt(response) < 0)
		        	response = getError(response);
		        System.out.println("receive: " + response);
		        // stop communication with server if client sent "bye" or "terminate"
		        if(response.equals("exit."))
		        	break;
	    	}	
	        // close streams and socket
	        outputStream.close();
	        inputStream.close();
	        socket.close();
	        // exit program
	        System.exit(0);
	    }
	    catch(Exception e) {
	    	System.out.println(e);
	    }
	}

	// interpets error code from server and returns corresponding error message
	// e.g. getError(-1) returns "incorrect operation command."
	private static String getError(String errCode) {
		String error = null;
		switch(errCode) {
			case "-1":
				error = "incorrect operation command.";
				break;
			case "-2":
				error = "number of inputs is less than two.";
				break;
			case "-3":
				error = "number of inputs is more than four.";
				break;
			case "-4":
				error = "one or more of the inputs contain(s) non­-integer(s).";
				break;
			case "-5":
				error = "exit.";
				break;
			default:
				break;
		}
		return error;
	}
}