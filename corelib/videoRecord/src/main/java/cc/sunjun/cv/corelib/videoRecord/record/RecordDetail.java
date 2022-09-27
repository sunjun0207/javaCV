package cc.sunjun.cv.corelib.videoRecord.record;

import java.io.IOException;

import org.bytedeco.javacv.FrameGrabber.Exception;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 录像器接口
 */

public interface RecordDetail {

	/**
	 * 设置视频源
	 * 
	 * @param src
	 *            视频源（文件或流媒体拉流地址）
	 * @return
	 */
	RecordDetail from(String src) throws Exception;

	/**
	 * 设置输出文件或推流到流媒体服务
	 * 
	 * @param out
	 *            输出文件或流媒体服务推流地址
	 * @return
	 * @throws IOException
	 */
	RecordDetail to(String out) throws IOException;

	/**
	 * 转发源视频到输出（复制）
	 * 
	 * @param src
	 *            视频源
	 * @param out
	 *            输出文件或流媒体服务推流地址
	 * @return
	 * @throws IOException
	 */
	RecordDetail stream(String src, String out) throws IOException;

	/**
	 * 设置音频参数
	 * 
	 * @param audioChannels
	 * @param audioBitrate
	 * @param sampleRate
	 * @return
	 */
	RecordDetail audioParam(int audioChannels, int audioBitrate, int sampleRate);

	/**
	 * 设置视频参数
	 * 
	 * @param width
	 * @param height
	 * @param framerate
	 * @param bitrate
	 * @return
	 */
	RecordDetail videoParam(Integer width, Integer height, int framerate, int bitrate);

	/**
	 * 开始录制
	 * 
	 * @return
	 */
	RecordDetail start();

	/**
	 * 暂停录制
	 * 
	 * @return
	 */
	RecordDetail pause();

	/**
	 * 继续录制（从暂停中恢复）
	 * 
	 * @return
	 */
	RecordDetail carryOn();

	/**
	 * 停止录制
	 * 
	 * @return
	 */
	RecordDetail stop();

	/**
	 * 延迟录制
	 * @param startTime：开始录制的时间
	 * @param duration：持续时长
	 * @throws IOException
	 * @throws InterruptedException
	 */
	void record(long startTime, long duration) throws IOException, InterruptedException;

}
