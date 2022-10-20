package cc.sunjun.cv.video.snapshot.web.util;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 公共工具类
 */
public class CommonUtil {

	public static void main(String[] args) {
		String[] arr = new String[] {"1","2"};
		System.err.println(isAllNullOrEmpty(arr));;
	}

	public static boolean isAllNullOrEmpty(String... str) {
		int len=-1;

		if (str == null||(len=str.length)<1) {
			return true;
		}

		for (String obj : str) {
			if (isNullOrEmpty(obj)) {
				len--;
			}
		}

		if(len<1) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmpty(String str) {
		return null == str || str.length() < 1 || "".equals(str.trim());
	}

	public static boolean isNullOrEmpty(String... str) {
		if (str == null) {
			return false;
		}
		for (String obj : str) {
			if (isNullOrEmpty(obj)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNull(Object obj) {
		return null == obj;
	}

	public static boolean isNull(Object... objarr) {
		if (objarr == null) {
			return false;
		}
		for (Object obj : objarr) {
			if (null == obj) {
				return true;
			}
		}
		return false;
	}
}
