package authentication;

import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Date;

public class ProtectedClient
{
	public void sendAuthentication(String user, String password, OutputStream outStream) throws IOException, NoSuchAlgorithmException 
	{
		DataOutputStream out = new DataOutputStream(outStream);

		// IMPLEMENT THIS FUNCTION.
		
		Date date = new Date();
		long timeStamp1 = date.getTime();
		long timeStamp2 = date.getTime();
		double rand_no1 = Math.random();
		double rand_no2 = Math.random();
		
		byte[] digest1 = Protection.makeDigest(user,password,timeStamp1,rand_no1);
		byte[] digest2 = Protection.makeDigest(digest1,timeStamp2,rand_no2);
		
		out.writeUTF(user);
		out.writeLong(timeStamp1);
		out.writeLong(timeStamp2);
		out.writeDouble(rand_no1);
		out.writeDouble(rand_no2);
		out.writeInt(digest1.length);
		out.write(digest2);
		
		out.flush();
	}

	public static void main(String[] args) throws Exception 
	{
		String host = "192.168.56.1";  //cse.unt.edu 192.168.56.1
		int port = 7999;
		String user = "George";
		String password = "abc123";
		Socket s = new Socket(host, port);

		ProtectedClient client = new ProtectedClient();
		client.sendAuthentication(user, password, s.getOutputStream());

		s.close();
	}
}