package cc.sunjun.cv.corelib.videoRecord;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 没有找到对应的编解码器
 */

public class CodecNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CodecNotFoundException(String message) {
		super(message);
	}
	
}
