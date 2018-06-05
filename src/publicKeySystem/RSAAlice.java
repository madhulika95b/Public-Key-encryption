package publicKeySystem;

import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Scanner;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;

public class RSAAlice {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Enter Choice - 1. Confidentiality 2. Integrity and Authentication :");
		Scanner input = new Scanner(System.in);
		String host = "192.168.56.1"; //cse.unt.edu
		int port = 7999;
		Socket s = new Socket(host,port);
		
		ObjectOutputStream BobPublicKey = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream AlicePublicKey = new ObjectInputStream(s.getInputStream());
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.genKeyPair();
		RSAPublicKey Alice_PubKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey Alice_PrivKey = (RSAPrivateKey) keyPair.getPrivate();
		BobPublicKey.writeObject(Alice_PubKey);
		RSAPublicKey eBob = (RSAPublicKey) AlicePublicKey.readObject();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		
		int choice = input.nextInt();
		
		switch(choice)
		{
		case 1:
			
			System.out.println("Confidentiality - ");
			Scanner input1 = new Scanner(System.in);
			System.out.println("Enter Plain Text:");
			String inp = input1.nextLine();
			cipher.init(Cipher.ENCRYPT_MODE,eBob);
			byte[] cipherText = cipher.doFinal(inp.getBytes());
			System.out.println("Cipher Text is : "+cipherText);
			BobPublicKey.writeInt(1);
			BobPublicKey.writeObject(cipherText);
			BobPublicKey.flush();
			BobPublicKey.close();
			break;
		
		case 2:
			
			System.out.println("Integrity and Authentication - ");
			Scanner input2 = new Scanner(System.in);
			System.out.println("Enter Plain Text:");
			String inp2 = input2.nextLine();
			cipher.init(Cipher.ENCRYPT_MODE,Alice_PrivKey);
			byte[] cipherTxt = cipher.doFinal(inp2.getBytes());
			System.out.println("Cipher Text is : "+cipherTxt);
			BobPublicKey.writeInt(2);
			BobPublicKey.writeObject(cipherTxt);
			BobPublicKey.flush();
			BobPublicKey.close();
			break;
		}
		s.close();
		input.close();
		
	}
}
