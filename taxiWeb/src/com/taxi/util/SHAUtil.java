package com.taxi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {

	private static MessageDigest md = null;
	
	public static String getSHA256(String str) throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("SHA-256");
		md.update(str.getBytes());
		return bytesToHex(md.digest());
	}

	public static String getSHA512(String str) throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("SHA-512");
		md.update(str.getBytes());
		return bytesToHex(md.digest());
	}
	
	private static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes)
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}

}
