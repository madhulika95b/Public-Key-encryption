package signature;

import java.io.*;
import java.net.*;
import java.security.*;
import java.math.BigInteger;

public class ElGamalAlice
{
	private static BigInteger computeY(BigInteger p, BigInteger g, BigInteger d)
	{
		// IMPLEMENT THIS FUNCTION;
		
		BigInteger y = g.modPow(d, p);
		
		return y;
	}

	private static BigInteger computeK(BigInteger p)
	{
		// IMPLEMENT THIS FUNCTION;
		
		SecureRandom rand = new SecureRandom();
		int length = 1024;
		BigInteger k = new BigInteger(length,rand);
		BigInteger x = p.subtract(BigInteger.ONE);
		
		while(!k.gcd(x).equals(BigInteger.ONE))
		{
			k = new BigInteger(length,rand);
		}
		
		return k;
	}
	
	private static BigInteger computeA(BigInteger p, BigInteger g, BigInteger k)
	{
		// IMPLEMENT THIS FUNCTION;
		
		BigInteger a = g.modPow(k, p);
		
		return a;
	}

	private static BigInteger computeB(	String message, BigInteger d, BigInteger a, BigInteger k, BigInteger p)
	{
		// IMPLEMENT THIS FUNCTION;
		
		BigInteger msg = new BigInteger(message.getBytes());
		BigInteger x = p.subtract(BigInteger.ONE);  // x = p-1
		
		BigInteger p1 = x;
		BigInteger y0 = BigInteger.ZERO;
		BigInteger y1 = BigInteger.ONE;
		BigInteger y2 = k;
		BigInteger z1,z2,z3;
		
		while(!y2.equals(BigInteger.ZERO))
		{
			z1 = p1.divide(y2);   // p1/y2 
			z2 = p1.subtract(y2.multiply(z1));   // p1-y2*z1
			p1 = y2;
			y2 = z2;
			z3 = y0.subtract(y1.multiply(z1));  // y0-y1*z1
			y0 = y1;
			y1 = z3;
		}
		
		BigInteger b = y0.multiply(msg.subtract(d.multiply(a))).mod(x); // b = ((msg-da)/k) mod (p-1)
		
		return b;
	}

	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";

		String host = "192.168.56.1";  //cse01.cse.unt.edu
		int port = 7999;
		Socket s = new Socket(host, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

		// You should consult BigInteger class in Java API documentation to find out what it is.
		BigInteger y, g, p; // public key
		BigInteger d; // private key

		int mStrength = 1024; // key bit length
		SecureRandom mSecureRandom = new SecureRandom(); // a cryptographically strong pseudo-random number

		// Create a BigInterger with mStrength bit length that is highly likely to be prime.
		// (The '16' determines the probability that p is prime. Refer to BigInteger documentation.)
		p = new BigInteger(mStrength, 16, mSecureRandom);
		
		// Create a randomly generated BigInteger of length mStrength-1
		g = new BigInteger(mStrength-1, mSecureRandom);
		d = new BigInteger(mStrength-1, mSecureRandom);

		y = computeY(p, g, d);

		// At this point, you have both the public key and the private key. Now compute the signature.

		BigInteger k = computeK(p);
		BigInteger a = computeA(p, g, k);
		BigInteger b = computeB(message, d, a, k, p);

		// send public key
		os.writeObject(y);
		os.writeObject(g);
		os.writeObject(p);

		// send message
		os.writeObject(message);
		
		// send signature
		os.writeObject(a);
		os.writeObject(b);
		
		s.close();
	}
}