package com.magotzis.mybatis.autobuild;

/**
 * 驼峰命名转下划线
 * @author jeff he
 *
 */
public class NameConverter {

	public static String conver(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成小写
			result.append(name.substring(0, 1).toLowerCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase())
						&& !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toLowerCase());
			}
		}
		return result.toString();
	}

}
