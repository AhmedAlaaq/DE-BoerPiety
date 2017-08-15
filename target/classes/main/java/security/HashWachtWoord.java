package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import exception.updateException;

public class HashWachtWoord {
	public static String hashWachtWoord(String wachtWoord, String salt)throws NoSuchAlgorithmException {
		StringBuilder sb = new StringBuilder();
		try{
			MessageDigest md = MessageDigest
				.getInstance("SHA-256"); /*
											 * kan ik er een SHA, SHA-1,
											 * SHA-256... inplaats van MD5
											 */
		String hash = wachtWoord + salt;
		md.update(hash.getBytes());
		byte[] b = md.digest();
		
		for (byte b1 : b) 
			sb.append(Integer.toHexString(b1  & 0xff).toString());
		
		}
		catch(NoSuchAlgorithmException ex){
			ex.printStackTrace();
		}
		return sb.toString();
	}
	
	
	

}
