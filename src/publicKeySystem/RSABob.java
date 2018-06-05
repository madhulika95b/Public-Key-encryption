package publicKeySystem;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import javax.crypto.Cipher;

public class RSABob {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();

		ObjectInputStream Alice_PublicKey = new ObjectInputStream(client.getInputStream());
		ObjectOutputStream PublicKey = new ObjectOutputStream(client.getOutputStream());
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024,new SecureRandom());
		KeyPair keyPair = keyPairGen.genKeyPair();
		RSAPublicKey BobPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey BobPrivKey = (RSAPrivateKey) keyPair.getPrivate();
		PublicKey.writeObject(BobPublicKey);
		
		RSAPublicKey eAlice = (RSAPublicKey) Alice_PublicKey.readObject();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		
		int choice = Alice_PublicKey.readInt();
		
		switch(choice)
		{
		case 1:
			System.out.println("Confidentiality - ");
			byte[] in1 = (byte[])Alice_PublicKey.readObject();
			cipher.init(Cipher.DECRYPT_MODE,BobPrivKey);
			byte[] plainText = cipher.doFinal(in1);
			System.out.println("Plain Text: "+new String(plainText));
			break;
		case 2:
			System.out.println("Integrity and Authentication:");
			byte[] in2 = (byte[])Alice_PublicKey.readObject();
			cipher.init(Cipher.DECRYPT_MODE,eAlice);
			byte[] plainTxt = cipher.doFinal(in2);
			System.out.println("Plain Text: "+new String(plainTxt));
			break;			
		}
				
	}

}
