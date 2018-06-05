package messageDigest;

import messageDigest.HashGenerator;

public class StringToHashConvertor {

	public static void main(String[] args) {
		
        try{
        	String input = args[0];
        	
        	System.out.println("Input String:" +input);
        	String md5Value = HashGenerator.generateMD5(input);
        	System.out.println("The MD5 Hash Value for the input string is:" +md5Value);
        	String sha1Value = HashGenerator.generateSHA1(input);
        	System.out.println("The SHA Hash Value for the input string is:"+sha1Value);
       /* 	String sha256Value = HashGenerator.generateSHA256(input);
        	System.out.println("The SHA-256 Hash Value for the input string is:"+sha256Value);
       */
        } catch (Exception e){
        	e.printStackTrace();
        }
	}

}
