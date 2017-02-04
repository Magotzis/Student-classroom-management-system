package com.magotzis.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class WriteHtml {
	public static synchronized void writeHtml(String filePath) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
			in  = new BufferedReader(isr);
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line + '\n');
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		String outStr = "<body><style type=\"text/css\">#svg2{display:none}</style><script type=\"text/javascript\">document.domain = \"medu2011.com\";</script>";
		if(sb.indexOf(outStr) < 0){
		String needStr = "<body>";
		int startindex = sb.indexOf(needStr);
		// 输入内容
		sb.replace(startindex, startindex + needStr.length(), outStr);
		
		BufferedWriter f = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
		f.write(sb.toString());
		f.flush();
		f.close();
		System.out.println("结束");
		}
	}
}
