package cc.sunjun.cv.corelib.videoRecord.record.impl;

import static org.bytedeco.javacpp.avcodec.*;

import java.io.IOException;

import cc.sunjun.cv.corelib.videoRecord.record.FFmpegFrameRecorderPlus;
import cc.sunjun.cv.corelib.videoRecord.record.RecordDetail;
import org.bytedeco.javacpp.avformat.AVFormatContext;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

import cc.sunjun.cv.corelib.videoRecord.work.RecordThread;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/9 13:38
 * @Description: JavaCV录像
 */

public class JavaCVRecordImpl implements RecordDetail {

	private static int threadInitNumber;

	private static synchronized int nextThreadNum() {
		return threadInitNumber++;
	}

	private final static String THREAD_NAME = "录像工作线程";
	private FFmpegFrameGrabber grabber = null;
	private FFmpegFrameRecorderPlus record = null;
	private String src;
	private String	out;
	private int width = -1;
	private int height = -1;

	// 视频参数
	protected int audiocodecid;
	protected int codecid;
	protected double framerate;// 帧率
	protected int bitrate;// 比特率

	// 音频参数
	// 想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
	private int audioChannels;
	private int audioBitrate;
	private int sampleRate;

	protected RecordThread cuThread;// 当前线程

	public JavaCVRecordImpl() {
		super();
	}

	public JavaCVRecordImpl(String src, String out) {
		super();
		this.src = src;
		this.out = out;
	}

	public JavaCVRecordImpl(String src, String out, int width, int height) {
		super();
		this.src = src;
		this.out = out;
		this.width = width;
		this.height = height;
	}

	public String getSrc() {
		return src;
	}

	public JavaCVRecordImpl setSrc(String src) {
		this.src = src;
		return this;
	}

	public String getOut() {
		return out;
	}

	public JavaCVRecordImpl setOut(String out) {
		this.out = out;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public JavaCVRecordImpl setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public JavaCVRecordImpl setHeight(int height) {
		this.height = height;
		return this;
	}

	public RecordDetail stream() throws IOException {
		return stream(src, out);
	}

	public int getAudioChannels() {
		return audioChannels;
	}

	public JavaCVRecordImpl setAudioChannels(int audioChannels) {
		this.audioChannels = audioChannels;
		return this;
	}

	public int getAudioBitrate() {
		return audioBitrate;
	}

	public JavaCVRecordImpl setAudioBitrate(int audioBitrate) {
		this.audioBitrate = audioBitrate;
		return this;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public JavaCVRecordImpl setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
		return this;
	}

	/**
	 * 视频源
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	@Override
	public RecordDetail from(String src) throws Exception {
		if (src == null) {
			throw new Exception("源视频不能为空");
		}
		this.src = src;
		// 采集/抓取器
		grabber = new FFmpegFrameGrabber(src);
		if (hasRTSP(src)) {
			grabber.setOption("rtsp_transport", "tcp");
		}
		grabber.start();// 开始之后ffmpeg会采集视频信息，之后就可以获取音视频信息
		if (width < 0 || height < 0) {
			width = grabber.getImageWidth();
			height = grabber.getImageHeight();
		}
		// 视频参数
		audiocodecid = grabber.getAudioCodec();
		codecid = grabber.getVideoCodec();
		framerate = grabber.getVideoFrameRate();// 帧率
		bitrate = grabber.getVideoBitrate();// 比特率
		// 音频参数
		// 想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
		audioChannels = grabber.getAudioChannels();
		audioBitrate = grabber.getAudioBitrate();
		if (audioBitrate < 1) {
			audioBitrate = 128 * 1000;// 默认音频比特率
		}
		sampleRate = grabber.getSampleRate();
		return this;
	}

	/**
	 * 音频参数设置
	 * 
	 * @param audioChannels
	 * @param audioBitrate
	 * @param sampleRate
	 * @return
	 */
	@Override
	public RecordDetail audioParam(int audioChannels, int audioBitrate, int sampleRate) {
		this.audioChannels = audioChannels;
		this.audioBitrate = audioBitrate;
		this.sampleRate = sampleRate;
		return this;
	}

	/**
	 * 视频参数设置
	 * 
	 * @param width
	 *            -可以为空，为空表示不改变源视频宽度
	 * @param height
	 *            -可以为空，为空表示不改变源视频高度
	 * @param framerate-帧率
	 * @param bitrate-比特率
	 * @return
	 */
	@Override
	public RecordDetail videoParam(Integer width, Integer height, int framerate, int bitrate) {
		if (width != null) {
			this.width = width;
		}
		if (height != null) {
			this.height = height;
		}
		this.framerate = framerate;
		this.bitrate = bitrate;
		return this;
	}

	/**
	 * 输出视频到文件或者流服务
	 * 
	 * @param out
	 *            -输出位置（支持流服务和文件）
	 * @return
	 * @throws IOException
	 */
	@Override
	public RecordDetail to(String out) throws IOException {
		if (out == null) {
			throw new Exception("输出视频不能为空");
		}
		this.out = out;
		// 录制/推流器
		record = new FFmpegFrameRecorderPlus(out, width, height);
		record.setVideoOption("crf", "18");
		record.setGopSize(2);
		record.setFrameRate(framerate);
		record.setVideoBitrate(bitrate);

		record.setAudioChannels(audioChannels);
		record.setAudioBitrate(audioBitrate);
		record.setSampleRate(sampleRate);
		AVFormatContext fc = null;
		//rtmp和flv
		if (hasRTMPFLV(out)) {
			// 封装格式flv，并使用h264和aac编码
			record.setFormat("flv");
			record.setVideoCodec(AV_CODEC_ID_H264);
			record.setAudioCodec(AV_CODEC_ID_AAC);
		}else if(hasMP4(out)){//MP4
			record.setFormat("mp4");
			record.setVideoCodec(AV_CODEC_ID_H264);
			record.setAudioCodec(AV_CODEC_ID_AAC);
		}
		record.start(fc);
		return this;
	}

	
	/*
	 * 是否包含rtmp或flv
	 */
	private boolean hasRTMPFLV(String str) {
		return str.indexOf("rtmp") > -1 || str.indexOf("flv") > 0;
	}
	
	/*
	 * 是否包含mp4
	 */
	private boolean hasMP4(String str) {
		return str.indexOf("mp4") > 0;
	}
	/*
	 * 是否包含rtsp
	 */
	private boolean hasRTSP(String str) {
		return str.indexOf("rtsp") > -1;
	}
	
	/**
	 * 转发源视频到输出（复制）
	 * 
	 * @param src
	 *            -源视频
	 * @param out
	 *            -输出流媒体服务地址
	 * @return
	 * @throws org.bytedeco.javacv.FrameRecorder.Exception
	 */
	@Override
	public RecordDetail stream(String src, String out) throws IOException {
		if (src == null || out == null) {
			throw new Exception("源视频和输出为空");
		}
		this.src = src;
		this.out = out;
		// 采集/抓取器
		grabber = new FFmpegFrameGrabber(src);
		grabber.start();
		if (width < 0 || height < 0) {
			width = grabber.getImageWidth();
			height = grabber.getImageHeight();
		}
		// 视频参数
		int audiocodecid = grabber.getAudioCodec();
		int codecid = grabber.getVideoCodec();
		double framerate = grabber.getVideoFrameRate();// 帧率
		int bitrate = grabber.getVideoBitrate();// 比特率

		// 音频参数
		// 想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
		int audioChannels = grabber.getAudioChannels();
		int audioBitrate = grabber.getAudioBitrate();
		int sampleRate = grabber.getSampleRate();

		// 录制/推流器
		record = new FFmpegFrameRecorderPlus(out, width, height);
		record.setVideoOption("crf", "18");
		record.setGopSize(1);

		record.setFrameRate(framerate);
		record.setVideoBitrate(bitrate);
		record.setAudioChannels(audioChannels);
		record.setAudioBitrate(audioBitrate);
		record.setSampleRate(sampleRate);
		AVFormatContext fc = null;
		//rtmp和flv
		if (hasRTMPFLV(out)) {
			// 封装格式flv，并使用h264和aac编码
			record.setFormat("flv");
			record.setVideoCodec(AV_CODEC_ID_H264);
			record.setAudioCodec(AV_CODEC_ID_AAC);
			if(hasRTMPFLV(src)) {
				fc = grabber.getFormatContext();
			}
		}else if(hasMP4(out)){//MP4
			record.setFormat("mp4");
			record.setVideoCodec(AV_CODEC_ID_H264);
			record.setAudioCodec(AV_CODEC_ID_AAC);
		}
		record.start(fc);
		return this;
	}

	/**
	 * 转封装
	 * 
	 * @throws IOException
	 */
	public void forward(){
		long startTime = System.currentTimeMillis();
		System.err.println("开始循环读取时间：" + startTime + " 毫秒");
		long errIndex = 0;// 采集或推流失败次数
		for (long i = 0; errIndex < Long.MAX_VALUE;) {
			AVPacket pkt = null;
			try {
				pkt = grabber.grabPacket();
				System.err.println("采集到的");
				if (pkt == null || pkt.size() <= 0 || pkt.data() == null) {// 空包结束
					break;
				}
				System.err.println("准备推:" + pkt.stream_index());
				if (record.recordPacket(pkt)) {
					System.err.println("推送成功：" + i++);
				}
				av_free_packet(pkt);
			} catch (Exception e) {// 推流失败
				errIndex++;
				System.err.println("采集失败:" + errIndex);
				continue;
			} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
				errIndex++;
				System.err.println("推流失败:" + errIndex);
				continue;
			} finally {
				//
			}
			System.err.println("时间：" + (System.currentTimeMillis() - startTime) + " 毫秒");
		}
	}

	/**
	 * 转码
	 * 
	 * @throws IOException
	 */
	public void codec() throws IOException {
		long startTime = System.currentTimeMillis();
		System.err.println("开始循环读取时间：" + startTime + " 毫秒");
		// 采集或推流失败次数
		long errIndex = 0;
		for ( ; errIndex < Long.MAX_VALUE; ) {
			try {
				Frame pkt = grabber.grabFrame();
				// 空包结束
				if (pkt == null) {
					record.stop();
					break;
				}
				record.record(pkt);
			} catch (Exception e) {// 推流失败
				record.stop();
				errIndex++;
				System.err.println("采集失败:" + errIndex);
				throw e;
			} catch (IOException e) {
				record.stop();
				errIndex++;
				System.err.println("录制失败:" + errIndex);
				throw e;
			}
			// System.err.println("推流后时间：" + (System.currentTimeMillis() - startTime));
		}
	}

	/**
	 * 延迟录制
	 * 
	 * @param startTime
	 *            -开始录制的时间
	 * @param duration
	 *            -持续时长
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public void record(long startTime, long duration) throws IOException, InterruptedException {
		// 采集或推流失败次数
		long errIndex = 0;
		long now = System.currentTimeMillis();
		long delay = startTime - now;
		if (startTime > 0 && delay > 0) {
			System.err.println("进入休眠状态，需要等待 " + delay + " 毫秒才开始录像");
			// 休眠
			Thread.sleep(delay);
		}

		for (; (now - startTime) <= duration; now = System.currentTimeMillis() ) {
			try {
				Frame pkt = grabber.grabFrame();
				// 采集空包结束
				if (pkt == null) {
					// 超过三次则终止录制
					if (errIndex > 3) {
						break;
					}
					errIndex++;
					continue;
				}
				record.record(pkt);
			} catch (Exception e) {// 采集失败
				record.stop();
				throw e;
			} catch (IOException e) {// 录制失败
				record.stop();
				throw e;
			}
		}
		record.stop();
	}

	/**
	 * 立即录制
	 * 
	 * @param duration
	 *            -持续时长
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void record(long duration) throws IOException{
		// 采集或推流失败次数
		long errIndex = 0;
		long now = System.currentTimeMillis();
		long startTime = now;
		for ( ; (now - startTime) <= duration; now = System.currentTimeMillis()) {
			System.err.println("持续录制 " + now + " 毫秒");
			try {
				Frame pkt = grabber.grabFrame();
				// 采集空包结束
				if (pkt == null) {
					// 超过三次则终止录制
					if (errIndex > 3) {
						break;
					}
					errIndex++;
					continue;
				}
				record.record(pkt);
			} catch (Exception e) {// 推流失败
				record.stop();
				throw e;
			} catch (IOException e) {
				record.stop();
				throw e;
			}
		}
		record.stop();
	}

	/**
	 * 开始
	 * 
	 * @return
	 */
	@Override
	public JavaCVRecordImpl start() {
		if (cuThread == null) {
			String name = THREAD_NAME + nextThreadNum();
			cuThread = new RecordThread(name, grabber, record, 1);
			cuThread.setDaemon(false);
			cuThread.start();
		} else {
			// 重置
			cuThread.reset(grabber, record);
			cuThread.carryOn();
		}

		return this;
	}

	/**
	 * 重新开始，实际链式调用了：from(src).to(out).start()
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public RecordDetail restart() throws Exception, IOException {
		return from(src).to(out).start();
	}

	/**
	 * 暂停
	 * 
	 * @return
	 */
	@Override
	public JavaCVRecordImpl pause() {
		if (cuThread != null && cuThread.isAlive()) {
			cuThread.pause();
		}
		return this;
	}

	/**
	 * 从暂停中恢复
	 * 
	 * @return
	 */
	@Override
	public JavaCVRecordImpl carryOn() {
		if (cuThread != null && cuThread.isAlive()) {
			cuThread.carryOn();
		}
		return this;
	}

	/**
	 * 停止录制线程和录制器
	 * 
	 * @return
	 */
	@Override
	public JavaCVRecordImpl stop() {
		if (cuThread != null && cuThread.isAlive()) {
			// 先结束线程，然后终止录制
			cuThread.over();
		}
		return this;
	}

}
