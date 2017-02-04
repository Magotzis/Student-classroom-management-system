package com.magotzis.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpFileDownload {  

    /** 
     * 将HTTP资源另存为文件 
     *  
     * @param destUrl 
     *            String 
     * @param fileName 
     *            String 
     * @throws Exception 
     */  
    public static void downloadHttpFile(HttpServletRequest request, HttpServletResponse response,String destUrl, String fileName) throws IOException {  
    	response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8"); 
        BufferedInputStream bis = null; 
        BufferedOutputStream bos = null;
        HttpURLConnection httpUrl = null;  
        
        response.setContentType("application/octet-stream");

		response.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));

		/*response.setHeader("Content-Length", String.valueOf(fileLength));*/

        URL url = null;  
        // 建立链接  
        url = new URL(destUrl);  
        httpUrl = (HttpURLConnection) url.openConnection();  
        // 连接指定的资源  
        httpUrl.connect();  
        // 获取网络输入流  
        bis = new BufferedInputStream(httpUrl.getInputStream());  
        bos = new BufferedOutputStream(response.getOutputStream());

		byte[] buff = new byte[2048];
		int bytesRead;

		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);

		}

		bis.close();
		bos.close();  
        httpUrl.disconnect();  
    }  
}  