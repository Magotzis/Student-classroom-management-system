package com.magotzis.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties prop;

	private static Properties uploadProp;

	public static Properties getProp(String name) {
		try {
			if (prop == null) {
				prop = new Properties();
				prop.load(PropertiesUtil.class.getClassLoader()
						.getResourceAsStream(name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static Properties getUploadProp() {
		try {
			if (uploadProp == null) {
				uploadProp = new Properties();
				uploadProp.load(PropertiesUtil.class.getClassLoader()
						.getResourceAsStream("upload.properties"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadProp;
	}

}
