package cc.sunjun.cv.corelib.videoSnapshot.core;

import java.io.ByteArrayOutputStream;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/9 13:38
 * @Description: ByteArrayOutputStream改进版 增加获取管理的数组
 */

public class ByteArrayOutputStreamPlus extends ByteArrayOutputStream {

	public ByteArrayOutputStreamPlus() {
		super();
	}

	public ByteArrayOutputStreamPlus(int i) {
		super(i);
	}

	public byte[] getBuf() {
		return this.buf;
	}

}
