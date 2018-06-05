package authentication;

import java.io.*;
import java.net.*;
import java.security.*;

public class ProtectedServer
{
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException 
	{
		DataInputStream in = new DataInputStream(inStream);

		// IMPLEMENT THIS FUNCTION.
		
		String user = in.readUTF();
		String password = lookupPassword(user);
		
		long timeStamp1 = in.readLong();
		long timeStamp2 = in.readLong();
		double rand_no1 = in.readDouble();
		double rand_no2 = in.readDouble();
		
		int length = in.readInt();
		byte[] digestReceived = new byte[length];
		in.readFully(digestReceived);
		boolean flag = true;
		byte[] digest1 = Protection.makeDigest(user,password,timeStamp1,rand_no1);
		byte[] digest2 = Protection.makeDigest(digest1,timeStamp2,rand_no2);
		
		flag = MessageDigest.isEqual(digestReceived,digest2);
		return flag;
	}

	protected String lookupPassword(String user)
	{ 
		
		return "abc123";
		
	}

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();

		ProtectedServer server = new ProtectedServer();

		if (server.authenticate(client.getInputStream()))
		  System.out.println("Client logged in.");
		else
		  System.out.println("Client failed to log in.");

		s.close();
	}
}