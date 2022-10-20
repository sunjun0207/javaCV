package cc.sunjun.cv.corelib.videoSnapshot.exception;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/9 13:38
 * @Description: 无法检索流信息
 */

public class StreamInfoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StreamInfoNotFoundException(String message) {
		super(message);
	}

}
