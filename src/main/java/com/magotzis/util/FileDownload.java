package com.magotzis.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownload {

	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName,
			String url) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		if(fileName==null ||fileName.equals("") ){
			fileName = url.substring(url.lastIndexOf("/")+1);
		}
		long fileLength = new File(url).length();

		response.setContentType("application/octet-stream");

		response.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));

		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(url));
		bos = new BufferedOutputStream(response.getOutputStream());

		byte[] buff = new byte[2048];
		int bytesRead;

		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);

		}

		bis.close();
		bos.close();

	}
}
