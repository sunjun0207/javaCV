package cc.sunjun.cv.corelib.videoSnapshot.exception;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 文件或流无法打开
 */

public class FileNotOpenException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FileNotOpenException(String message) {
		super(message);
	}

}
