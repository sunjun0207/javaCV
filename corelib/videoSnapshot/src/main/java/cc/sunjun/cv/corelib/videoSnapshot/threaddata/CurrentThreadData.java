package cc.sunjun.cv.corelib.videoSnapshot.threaddata;

import cc.sunjun.cv.corelib.videoSnapshot.core.Base64Plus;
import cc.sunjun.cv.corelib.videoSnapshot.core.Base64Plus.Encoder;
import cc.sunjun.cv.corelib.videoSnapshot.core.ByteArrayOutputStreamPlus;
import cc.sunjun.cv.corelib.videoSnapshot.grabber.impl.FFmpegVideoImageGrabberImpl;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 当前线程共享数据
 */

public class CurrentThreadData {
	
	public final static String DETAULT_FORMAT = "jpg";

	public final static ThreadLocal<FFmpegVideoImageGrabberImpl> grabber = new ThreadLocal<FFmpegVideoImageGrabberImpl>() {
		@Override
		protected FFmpegVideoImageGrabberImpl initialValue() {
			return new FFmpegVideoImageGrabberImpl();
		}
	};

	public static final ThreadLocal<Encoder> localEncoder = new ThreadLocal<Encoder>() {
		@Override
		protected Encoder initialValue() {
			return Base64Plus.getEncoder();
		}; 
	};
	
	public static final ThreadLocal<ByteArrayOutputStreamPlus> localbaos = new ThreadLocal<ByteArrayOutputStreamPlus>() {
		@Override
		protected ByteArrayOutputStreamPlus initialValue() {
			ByteArrayOutputStreamPlus baos = new ByteArrayOutputStreamPlus(1280 * 720 * 3);
			return baos;
		}; 
		@Override
		public ByteArrayOutputStreamPlus get() {
			ByteArrayOutputStreamPlus baos = super.get();
			baos.reset();
			return baos;
		}
	};
	
}
