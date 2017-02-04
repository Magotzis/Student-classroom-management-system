package com.magotzis.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts.upload.FormFile;

/**
 * 文件工具类
 */
public class FileUtil {

	/**
	 * 获取文件的大小(byte)
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getSize(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		return fis.available();
	}

	/**
	 * 获取文件的扩展名
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getExtName(String fileName) throws Exception {
		String extName = "";
		String[] temp = fileName.split("\\.");
		if (temp != null) {
			extName = temp[temp.length - 1];
		}
		return extName;
	}

	/**
	 * 根据文件名获取ContentType
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getContentType(String fileName) throws Exception {

		String contentType = "application/octet-stream";// 默认值

		String extName = getExtName(fileName);

		// 常用的扩展名(并非全部)
		if ("bmp".equals(extName)) {
			contentType = "application/x-bmp";
		} else if ("css".equals(extName)) {
			contentType = "text/css";
		} else if ("dll".equals(extName)) {
			contentType = "application/x-msdownload";
		} else if ("doc".equals(extName)) {
			contentType = "application/msword";
		} else if ("dot".equals(extName)) {
			contentType = "application/msword";
		} else if ("exe".equals(extName)) {
			contentType = "application/x-msdownload";
		} else if ("gif".equals(extName)) {
			contentType = "image/gif";
		} else if ("jpg".equals(extName)) {
			contentType = "image/jpeg";
		} else if ("pdf".equals(extName)) {
			contentType = "application/pdf";
		} else if ("png".equals(extName)) {
			contentType = "image/png";
		} else if ("ppt".equals(extName)) {
			contentType = "application/vnd.ms-powerpoint";
		} else if ("pps".equals(extName)) {
			contentType = "application/vnd.ms-powerpoint";
		} else if ("xls".equals(extName)) {
			contentType = "application/vnd.ms-excel";
		} else if ("txt".equals(extName)) {
			contentType = "text/plain";
		} else {
			contentType = "application/octet-stream";
		}

		return contentType;
	}

	/**
	 * 更改文件目录(剪切文件并覆盖)
	 * 
	 * @param originalDir
	 *            原路径（包括文件名）
	 * @param targetDir
	 *            目标路径（不包括文件名）
	 * @throws Exception
	 */
	public static void changeFileDir(String originalDir, String targetDir)
			throws Exception {

		File originalFile = new File(originalDir); // 源文件
		File targetPath = new File(targetDir); // 目标文件夹

		// 判断文件夹是否存在
		if (!targetPath.exists()) {
			targetPath.mkdirs(); // 不存在则创建
		}

		// 将文件移到新文件里
		File targetlFile = new File(targetDir + "/" + originalFile.getName());

		if (targetlFile.exists()) {// 如果目标文件存在则删除
			targetlFile.delete();
		}

		originalFile.renameTo(targetlFile);

		if (originalFile.exists()) {// 如果源文件还存在则删除
			originalFile.delete();
		}
	}

	/**
	 * 检查文件夹是否存在，不存在则创建
	 * 
	 * @param dir
	 * @throws Exception
	 */
	public static void markDir(String dir) throws Exception {
		File file = new File(dir);
		// 判断文件夹是否存在
		if (!file.exists()) {
			file.mkdirs(); // 不存在则创建
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param formFile
	 * @param fileDir
	 *            保存的文件目录
	 */
	public static void saveFile(FormFile formFile, String fileDir) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = formFile.getInputStream();
			FileUtil.markDir(fileDir);
			os = new FileOutputStream(fileDir + formFile.getFileName());
			byte[] buf = new byte[1024];
			int i = -1;
			while ((i = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, i);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 保存文件
	 * 
	 * @param content
	 *            文件内容
	 * @param fileDir
	 *            保存的文件目录
	 * @param fileName
	 *            保存的文件名
	 */
	public static void saveFile(String content, String fileDir, String fileName) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new ByteArrayInputStream(content.getBytes());
			FileUtil.markDir(fileDir);
			os = new FileOutputStream(fileDir + fileName);
			byte[] buf = new byte[1024];
			int i = -1;
			while ((i = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, i);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 读取txt文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxt(String fileName) {

		BufferedReader br = null;
		StringBuffer str = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(fileName));
			String r = br.readLine();
			while (r != null) {
				str.append(r);
				// str.append("\n");
				r = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return str.toString();

	}
}
