package security;

import java.util.Random;

public class SaltRandom {
	public static String SaltRandom() {
		Random random = new Random();
		String alphabetString = "ABCDEFGHIGKLMNOPQRSTUWXYZ0123456789";
		char[] alphabetAraay = alphabetString.toCharArray();
		StringBuilder salt = new StringBuilder();
		for (int i = 0; i < 64 ; i++) {
			salt.append(alphabetAraay[random.nextInt(alphabetAraay.length)]);

		}

		return salt.toString();
	}

}
