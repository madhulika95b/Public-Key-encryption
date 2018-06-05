package x509;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class Server {

	public static void main(String[] args) throws Exception {
		
		String name="store";
		char[] password="security".toCharArray();
		/*for(int i=0;i<password.length;i++)
			System.out.println("Password:"+password);
		*/
		int port = 7999;
		ServerSocket ss = new ServerSocket(port);
		Socket s = ss.accept();
		ObjectInputStream is = new ObjectInputStream(s.getInputStream());
		KeyStore ks=KeyStore.getInstance("jks");
		ks.load(new FileInputStream("C:\\Users\\psnpp\\workspace\\CS_Project\\keystore.jks"),null);
		PrivateKey pk = (PrivateKey)ks.getKey(name, password);
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		byte[] in = (byte[]) is.readObject();
		//System.out.println("Ef"+in);
		cipher.init(Cipher.DECRYPT_MODE, pk);
		byte[] plainText=cipher.doFinal(in);
		System.out.println("Plain Text:" +new String(plainText));
		ss.close();
		

	}

}
