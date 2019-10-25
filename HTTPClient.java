// Author: Branson Pigg

import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPClient {

	public static void main(String[] args) {
		
		String req;
		
		Scanner in = new Scanner(System.in);
		
		while (in.hasNextLine()) {
			try {
				Socket clientSocket = new Socket("comp431afa19", Integer.parseInt(args[0]));
				req = in.nextLine();
				BufferedReader inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		        outToServer.writeBytes(req + '\n');
		        String inServ = null;
		        while ((inServ = inFromServer.readLine()) != null) {
		        		System.out.println(inServ + "\r");
		        }
		        clientSocket.close();
			} catch (IOException e) {
				System.exit(0);
			}
		}
		in.close();
	}
}
