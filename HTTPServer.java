// Author: Branson Pigg

import java.nio.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.net.*;
import java.io.*;

public class HTTPServer {
	
	public static void main(String[] args) throws Exception {
		String clientReq;
		
		try {
			
			while (true) {
				ServerSocket welcomeSocket = new ServerSocket(14020);
				Socket connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				
				clientReq = inFromClient.readLine() +"\r\n";
				
				outToClient.writeBytes(clientReq);
				
				parse(clientReq, outToClient);
				
				/* String method = "";
				String absPath = "";
				String version = "";
				String[] tokens = clientReq.split("\\s+");
				method = tokens[0];
				absPath = tokens[1];
				version = tokens[2];
				if (!method.equals("GET")) {
					outToClient.writeChars("ERROR -- Invalid Method token.");
					break;
				}
				Path p = Paths.get(absPath);
				boolean isAbs = p.isAbsolute();
				if (!isAbs) {
					outToClient.writeBytes("ERROR --   Invalid Absolute-Path token.");
					break;
				} else {
					char[] absArray = absPath.toCharArray();
					for (char c : absArray) {
						if ((c != '.' && c != '_' && c != '/' && !Character.isDigit(c) && !Character.isLetter(c)) || c == '?') {
							outToClient.writeBytes("ERROR --   Invalid Absolute-Path token.");
							break;
						}
					}
				}
				char[] versArray = version.toCharArray();
				if (versArray.length != 8) {
					outToClient.writeBytes("ERROR --   Invalid HTTP-Version token.");
					break;
				} else if (!version.substring(0, 4).equals("HTTP") || versArray[4] != '/' || !Character.isDigit(versArray[5]) || versArray[6] != '.'
								|| !Character.isDigit(versArray[7])) {
					outToClient.writeBytes("ERROR --   Invalid HTTP-Version token.");
					break;
				}
				if (tokens.length > 3 && tokens[3] != null) {
					outToClient.writeBytes("ERROR --   Spurious token before CRLF.");
					break;
				}
				// outToClient.writeChars("Method = " +method);
				// outToClient.writeBytes("Request-URL = " +absPath);
				// outToClient.writeBytes("HTTP-Version = " +version);
				String exten = absPath.substring(absPath.length() - 5);
				if (!exten.contains(".html") && !exten.contains(".txt") 
						&& !exten.contains(".htm")) {
					outToClient.writeBytes("501 Not Implemented: " +absPath);
					break;
				}
				try {
					File req = new File(absPath);
					Scanner sc = new Scanner(req);
					while (sc.hasNextLine()) {
						outToClient.writeBytes(sc.nextLine() +"\r");
					}
					sc.close();
					break;
					
				} catch (FileNotFoundException e) {
					outToClient.writeBytes("404 Not Found: " + absPath);
					break;
				} catch (Exception e) {
					outToClient.writeBytes("ERROR: " +e.toString());
					break;
				} */
				connectionSocket.close();
				welcomeSocket.close();

			}
		} catch (SocketException e) {
				System.out.println("Connection error.");
				System.exit(0);
		}
		
		
	}
	
public static void parse(String clientReq, DataOutputStream outToClient) throws IOException {
	String method = "";
	String absPath = "";
	String version = "";
	String[] tokens = clientReq.split("\\s+");
	method = tokens[0];
	absPath = tokens[1];
	version = tokens[2];
	if (!method.equals("GET")) {
		outToClient.writeChars("ERROR -- Invalid Method token.");
		return;
	}
	Path p = Paths.get(absPath);
	boolean isAbs = p.isAbsolute();
	if (!isAbs) {
		outToClient.writeBytes("ERROR --   Invalid Absolute-Path token.");
		return;
	} else {
		char[] absArray = absPath.toCharArray();
		for (char c : absArray) {
			if ((c != '.' && c != '_' && c != '/' && !Character.isDigit(c) && !Character.isLetter(c)) || c == '?') {
				outToClient.writeBytes("ERROR --   Invalid Absolute-Path token.");
				return;
			}
		}
	}
	char[] versArray = version.toCharArray();
	if (versArray.length != 8) {
		outToClient.writeBytes("ERROR --   Invalid HTTP-Version token.");
		return;
	} else if (!version.substring(0, 4).equals("HTTP") || versArray[4] != '/' || !Character.isDigit(versArray[5]) || versArray[6] != '.'
					|| !Character.isDigit(versArray[7])) {
		outToClient.writeBytes("ERROR --   Invalid HTTP-Version token.");
		return;
	}
	if (tokens.length > 3 && tokens[3] != null) {
		outToClient.writeBytes("ERROR --   Spurious token before CRLF.");
		return;
	}
	// outToClient.writeChars("Method = " +method);
	// outToClient.writeBytes("Request-URL = " +absPath);
	// outToClient.writeBytes("HTTP-Version = " +version);
	String exten = absPath.substring(absPath.length() - 5);
	if (!exten.contains(".html") && !exten.contains(".txt") 
			&& !exten.contains(".htm")) {
		outToClient.writeBytes("501 Not Implemented: " +absPath);
		return;
	}
	try {
		File req = new File(absPath);
		Scanner sc = new Scanner(req);
		while (sc.hasNextLine()) {
			outToClient.writeBytes(sc.nextLine() +"\r");
		}
		sc.close();
		return;
		
	} catch (FileNotFoundException e) {
		outToClient.writeBytes("404 Not Found: " + absPath);
		return;
	} catch (Exception e) {
		outToClient.writeBytes("ERROR: " +e.toString());
		return;
	}
		
	}
} 
