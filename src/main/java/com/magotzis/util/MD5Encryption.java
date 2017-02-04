package com.magotzis.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;


/**
 * MD5加密算法
 * @Title: MD5Encryption.java 
 * @Description: 利用MD5算法加密
 * @author	jeff he
 * @date 2012-10-7
 * @version V1.0
 */
public final class MD5Encryption {

	public static String getMD5(String str) {
		Logger log = Logger.getLogger("MD5Encryption");
		String reStr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte ss[] = md.digest();
			reStr = bytes2String(ss).toLowerCase();
		} catch (Exception e) {
			log.error("[MD5加密失败]异常信息 ：", e);
		}
		return reStr;
	}
	
	public static String bytes2String(byte[] aa) {
		String hash = "";
		for(int i = 0; i < aa.length; i++) {
			int temp;
			temp = aa[i] < 0 ? aa[i]+256 : aa[i];
			if(temp < 16) hash += "0";
			hash += Integer.toString(temp, 16);
		}
		hash = hash.toUpperCase();
		return hash;
	}
}
