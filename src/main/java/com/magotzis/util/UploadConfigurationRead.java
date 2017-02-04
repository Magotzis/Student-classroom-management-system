package com.magotzis.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;


/**
 * @Description: 上传文件辅助工具类
 *
 */
public class UploadConfigurationRead {


	private static String PFILE = "upload.properties";

	@SuppressWarnings("unused")
	private URI uri = null;


	private long m_lastModifiedTime = 0;


	private File m_file = null;

	private Properties m_props = null;


	private static UploadConfigurationRead m_instance = new UploadConfigurationRead();


	private UploadConfigurationRead() {
		try {
			m_lastModifiedTime = getFile().lastModified();
			if (m_lastModifiedTime == 0) {
				System.err.println(PFILE + "file does not exist!");
			}
			m_props = new Properties();
			m_props.load(new FileInputStream(getFile()));

		} catch (URISyntaxException e) {
			System.err.println(PFILE+"文件路径不正确");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(PFILE+"文件读取异常");
			e.printStackTrace();
		}
	}



	private File getFile() throws URISyntaxException {
		URI fileUri = this.getClass().getClassLoader().getResource(PFILE).toURI();
		m_file = new File(fileUri);
		return m_file;
	}

	public synchronized static UploadConfigurationRead getInstance() {
		return m_instance;
	}


	public String getConfigItem(String name, String defaultVal) {
		long newTime = m_file.lastModified();

		if (newTime == 0) {
		
			if (m_lastModifiedTime == 0) {
				System.err.println(PFILE + " file does not exist!");
			} else {
				System.err.println(PFILE + " file was deleted!!");
			}
			return defaultVal;
		} else if (newTime > m_lastModifiedTime) {
			m_props.clear();
			try {
				m_props.load(new FileInputStream(getFile()));
			} catch (Exception e) {
				System.err.println("文件重新读取异常");
				e.printStackTrace();
			}
		}
		m_lastModifiedTime = newTime;
		String val = m_props.getProperty(name);
		if (val == null) {
			return defaultVal;
		} else {
			return val;
		}
	}


	public String getConfigItem(String name) {
		return getConfigItem(name, "");
	}
	
}
