package cc.sunjun.cv.corelib.videoRecord;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 无法检索流信息
 */

public class StreamInfoNotFoundException extends RuntimeException {

	public StreamInfoNotFoundException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
