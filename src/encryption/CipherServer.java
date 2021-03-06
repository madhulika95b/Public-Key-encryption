package encryption;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherServer
{
	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();

		// YOU NEED TO DO THESE STEPS:
		// -Read the key from the file generated by the client.
		// -Use the key to decrypt the incoming message from socket s.		
		// -Print out the decrypt String to see if it matches the orignal message.
		
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Key key = (Key) in.readObject();
		
		Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE,key);
		CipherInputStream cin = new CipherInputStream(s.getInputStream(),c);
		int n = cin.read();
		StringBuilder plainText = new StringBuilder();
		while(n!=-1)
		{
			plainText.append((char)n);
			n=cin.read();
		}
		System.out.println("The Plain Text after Decryption is: "+plainText.toString());
		in.close();
		cin.close();
		server.close();
		
	}
}