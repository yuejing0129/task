package com.jing.system.utils;

import java.security.MessageDigest;

/**
 * MD5 Class
 * @author jing.yue
 * @version 1.0
 * @since 2012-09-24
 *
 */
public class MD5Util {

	private static MD5Util fiveClass;
	private static final String hexDigits[] = {
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"A", "B", "C", "D", "E", "F"
	};

	private MD5Util() {
	}

	public static synchronized MD5Util getInstance() {
		if (fiveClass == null)
			fiveClass = new MD5Util();
		return fiveClass;
	}

	private String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString().toUpperCase();
	}

	private String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return (new StringBuilder(String.valueOf(hexDigits[d1]))).append(hexDigits[d2]).toString();
	}

	private byte[] md5Digest(byte src[]) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		return alg.digest(src);
	}
	
	public String getEncString(String strMing) {
		return getEncString(strMing, null);
	}

	public String getEncString(String strMing, String expStr) {
		String resultString = null;
		if(expStr == null) {
			resultString = new String(strMing);
		} else {
			resultString = new String(strMing + expStr);
		}
		try {
			resultString = byteArrayToHexString(md5Digest(resultString.getBytes()));
		}
		catch (Exception exception) { }
		return resultString;
	}

	public static void main(String args[]) {
		String str1 = "admin";
		String str2 = getInstance().getEncString(str1, "admin");
		System.out.println((new StringBuilder("明文:")).append(str1).toString());
		System.out.println((new StringBuilder("密文:")).append(str2).toString());
	}

}
