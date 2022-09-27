package cc.sunjun.cv.corelib.videoSnapshot;

import java.awt.image.BufferedImage;
import java.io.IOException;

import cc.sunjun.cv.corelib.videoSnapshot.core.JavaImgConverter;
import cc.sunjun.cv.corelib.videoSnapshot.grabber.impl.FFmpegVideoImageGrabberImpl;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 抓取器测试类
 */

public class GrabberTest {


	/**
	 * test
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//		ByteBuffer buf = new VideoFrameGrabber("rtmp://live.hkstv.hk.lxdns.com/live/hks").grab();
//		ByteBuffer buf = new VideoFrameGrabber().grabBuffer();
//		BufferedImage image = new FFmpegVideoImageGrabberImpl("rtmp://live.hkstv.hk.lxdns.com/live/hks").grabBufferImage();
		BufferedImage image = new FFmpegVideoImageGrabberImpl("http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8").grabBufferImage();
//		JavaImgConverter.viewBGR(1280, 720, buf);
//		BufferedImage image = JavaImgConverter.BGR2BufferedImage(buf, 1280,720);
		JavaImgConverter.viewImage(image);
	}


	
}
