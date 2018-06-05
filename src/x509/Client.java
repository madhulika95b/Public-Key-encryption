package x509;

import java.io.*;
import java.net.*;
import java.security.cert.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Scanner;
import javax.crypto.*;

public class Client {

	public static void main(String[] args) throws Exception {
		
		String host="192.168.56.1"; //cse.unt.edu
		int port = 7999;
		Socket s = new Socket(host,port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		InputStream ins = new FileInputStream("C:\\Users\\psnpp\\workspace\\CS_Project\\x509Certi.cer");
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate)cf.generateCertificate(ins);
		ins.close();
		System.out.println("Contents of Certificate:");
		System.out.println(cert.toString());
		
		Date date = cert.getNotAfter();
		if(date.after(new Date()))
		{
			System.out.println("Certificate is Valid From "+cert.getNotBefore()+ " to "+cert.getNotAfter());
			
		}
		else
		{
			System.out.println("Certificate Expired");
			
		}
		try
		{
			cert.checkValidity();
			System.out.println("Certificate is Valid");
			
		}catch(Exception e){
			System.out.println(e);
		}
		
		System.out.println("Enter String:");
		Scanner input = new Scanner(System.in);
		String msg = input.nextLine();
		
		RSAPublicKey eServer = (RSAPublicKey)cert.getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE,eServer);
		byte[] cipherText = cipher.doFinal(msg.getBytes());
		System.out.println("Cipher Text: "+cipherText);
		os.writeObject(cipherText);
		os.flush();
		os.close();
		s.close();
		input.close();
		

	}

}
