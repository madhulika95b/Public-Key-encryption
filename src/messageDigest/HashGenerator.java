package messageDigest;

import java.security.MessageDigest;

public class HashGenerator {
	private HashGenerator(){
		
	}
	public static String generateMD5(String message) throws Exception{
		return hashValue(message,"MD5");
	}
	public static String generateSHA1(String message) throws Exception{
		return hashValue(message,"SHA");
	}
	/*public static String generateSHA256(String message) throws Exception{
		return hashValue(message,"SHA-256");
	}
	*/
	private static String hashValue(String message, String algorithm) throws Exception{
		try{
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			//System.out.println("Hash Value: "+digest);
			byte[] hashResult = digest.digest(message.getBytes("UTF-8"));
			return convertBytesToHexString(hashResult);
		} catch(Exception e)
		{
			throw new Exception("Could not generate Hash Value",e);
		}
	}
	
	private static String convertBytesToHexString(byte[] array){
		StringBuffer stringBuffer = new StringBuffer();
		for(int i=0; i < array.length; i++) {
			stringBuffer.append(Integer.toString((array[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}
}
