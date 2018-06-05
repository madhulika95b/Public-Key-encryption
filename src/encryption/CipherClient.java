package encryption;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherClient
{
	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "192.168.56.1";  //cse01.cse.unt.edu
		int port = 7999;
		Socket s = new Socket(host, port);

		// YOU NEED TO DO THESE STEPS:
		// -Generate a DES key.
		// -Store it in a file.
		// -Use the key to encrypt the message above and send it over socket s to the server.
		
		KeyGenerator k = KeyGenerator.getInstance("DES");
		SecureRandom random = new SecureRandom();
		k.init(random);
		Key key = k.generateKey();
		
		File KeyFile = new File("Key.txt");
		ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(KeyFile));
		out1.writeObject(key);
		ObjectOutputStream servOut = new ObjectOutputStream(s.getOutputStream());
		servOut.writeObject(key);
		servOut.flush();
		
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		CipherOutputStream cipherOut = new CipherOutputStream(s.getOutputStream(),cipher);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		byte cipherText[] = message.getBytes();
		System.out.println("Text Before Encryption (Plain Text): "+message);
		System.out.println("Text After Encryption (Cipher Text): "+cipherText);
		cipherOut.write(cipherText);
		cipherOut.close();
		s.close();
		out1.close();		
		
	}
}