package cc.sunjun.cv.corelib.videoSnapshot.exception;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 没有找到对应的编解码器
 */

public class CodecNotFoundExpception extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CodecNotFoundExpception(String message) {
		super(message);
	}
	
}
