package com.magotzis.util;

public class StringUtil {
	
	public static boolean isEmpty(String str){
		boolean flag= false;
		if(str==null || str.equals("")){
			flag = true;
		}
		return flag;
	}
}
