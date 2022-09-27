package cc.sunjun.cv.corelib.videoRecord;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 文件或流无法打开
 */

public class FileNotOpenException extends RuntimeException{
	
	public FileNotOpenException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;
}
