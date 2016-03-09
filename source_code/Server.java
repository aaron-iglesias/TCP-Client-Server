import java.io.*; 
import java.net.*;
import java.util.*;

public class Server {

	// last 4 digits of UF ID
	private static String PORT = "9151";

	public static void main(String[] args) {
		ServerSocket operationServer = null;
        String line = null;
        BufferedReader inputStream = null;
        PrintStream outputStream = null;
        Socket clientSocket = null;

        // try to open a server socket on PORT
        try {
        	if(args.length != 0) {
        		PORT = args[0];
        		operationServer = new ServerSocket(Integer.parseInt(PORT));
        		System.out.println("./server " + PORT);
        	}
        	else {
        		operationServer = new ServerSocket(Integer.parseInt(PORT));
        		System.out.println("./server " + PORT);
        	}
        }
        catch(IOException e) {
        	System.out.println(e);
        }
        while(true) {
	        try {
	            clientSocket = operationServer.accept();
	            // print out Client's IP Address
	            System.out.println("get: connection from... " + clientSocket.getRemoteSocketAddress().toString());
	            // create input and output streams to communicate with client
	            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	            outputStream = new PrintStream(clientSocket.getOutputStream());
	            // continuously handle clients' requests
	            while(true) {
	            	// listen for data from client
	            	line = inputStream.readLine();
		            // parse client's request by space characters
					// e.g. "multiply 4 3" -> [multiply, 4, 3]
					String delims = "[ ]+";
		            String[] lineTokens = line.trim().toLowerCase().split(delims);
		            String response = getSolution(lineTokens);
		            outputStream.println(response);
		            System.out.println(getOutput(lineTokens, response));
		            // exit program if client sends "terminate"
		            if(lineTokens.length == 1 && lineTokens[0].equals("bye"))
		           		break;
		           	// gracefully terminate server if client sent "terminate"
		            if(lineTokens.length == 1 && lineTokens[0].equals("terminate")) {
		            	// close all streams and socket
		            	inputStream.close();
		            	outputStream.close();
	        			operationServer.close();
	        			// exit program
		           		System.exit(0);
		            }
	           }
	        }   
	    	catch (IOException e) {
	            System.out.println(e);
	        }
	    }
    }

    // returns output sent to client
    // e.g. getOutput(["add", "5", "4"], 9) returns "get: add 5 4, return: 9"
    private static String getOutput(String[] tokens, String solution) {
    	String output = "get:";
    	for(int i = 0; i < tokens.length; ++i)
    		output = output + " " + tokens[i];
    	output = output + ", return: " + solution;
    	return output;
    }

    // calculate Client's operation
    // e.g. getSolution(["add", "5", "4"]) returns "9"
	private static String getSolution(String[] tokens) {
		int len = tokens.length;

		// return relevant error codes
		if(len == 1 && (tokens[0].equals("bye") || tokens[0].equals("terminate")))
			return "-5";
		if(len == 0 || !(tokens[0].equals("add") || tokens[0].equals("subtract") || tokens[0].equals("multiply") || tokens[0].equals("divide")))
			return "-1";
		if(len < 3) 
			return "-2";
		if(len > 5)
			return "-3";
		for(int i = 1; i < len; ++i) {
			if(!isInteger(tokens[i]))
				return "-4";
		}

		// calculate solution with relevant operator
		double solution = Integer.parseInt(tokens[1]);
		if(tokens[0].equals("add")) {
			for(int i = 2; i < len; ++i)
				solution += Integer.parseInt(tokens[i]);
		}
		else if(tokens[0].equals("subtract")) {
			for(int i = 2; i < len; ++i)
				solution -= Integer.parseInt(tokens[i]);
		}
		else if(tokens[0].equals("multiply")) {
			for(int i = 2; i < len; ++i) {
				solution *= Integer.parseInt(tokens[i]);
			}
		}
		else {
			for(int i = 2; i < len; ++i) {
				solution /= Integer.parseInt(tokens[i]);
			}
		}

		// truncate unncessary .0 if double is actually an integer value
		// e.g. 8.0 returns 8
		// e.g. 8.1 returns 8.1
		//if(solution % 1 == 0)
			return Integer.toString((int)solution);
		//else
			//return Double.toString(solution);
	}

	// checks if String s is an integer
	// e.g. isInteger("5") returns true
	// e.g. isInteger("a") returns false
	private static boolean isInteger(String s) {
		// trim to remove leading and trailing white space
		s = s.trim();
		int len = s.length();
	    if(len == 0) 
	    	return false;
	    // define valid integer characters
	    // O(1) access
	    final Set<Character> intSet = new HashSet<Character>(Arrays.asList(
	    	new Character[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
			));
	    // iterate through each character of String s
	    // return false if character is not integer character
	    for(int i = 0; i < len; ++i) {
	    	if(!intSet.contains(s.charAt(i)))
	    		return false;
	    }
	    // return true if each character is integer
	    return true;
	}

}