package cc.sunjun.cv.corelib.videoRecord.work;

import java.io.IOException;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import cc.sunjun.cv.corelib.videoRecord.record.FFmpegFrameRecorderPlus;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 录制任务工作线程
 */

public class RecordThread extends Thread {
	
	protected FFmpegFrameGrabber grabber = null;
	protected FFmpegFrameRecorderPlus record = null;
	
	/**
	 * 运行状态，0-初始状态，1-运行，2-停止
	 */
	protected volatile int status = 0;
	//是否暂停，1-暂停
	protected volatile int pause = 0;
	//默认错误数量达到三次终止录制
	protected int errStopNum = 3;
	
	public RecordThread(String name, FFmpegFrameGrabber grabber, FFmpegFrameRecorderPlus record, Integer errStopNum) {
		super(name);
		this.grabber = grabber;
		this.record = record;
		if(errStopNum != null) {
			this.errStopNum = errStopNum;
		}
	}
	/**
	 * 运行过一次后必须进行重置参数和运行状态
	 */
	public void reset(FFmpegFrameGrabber grabber, FFmpegFrameRecorderPlus record) {
		this.grabber = grabber;
		this.record = record;
		this.status = 0;
	}
	
	public int getErrStopNum() {
		return errStopNum;
	}
	
	public void setErrStopNum(int errStopNum) {
		this.errStopNum = errStopNum;
	}
	
	public FFmpegFrameGrabber getGrabber() {
		return grabber;
	}

	public void setGrabber(FFmpegFrameGrabber grabber) {
		this.grabber = grabber;
	}

	public FFmpegFrameRecorderPlus getRecord() {
		return record;
	}

	public void setRecord(FFmpegFrameRecorderPlus record) {
		this.record = record;
	}

	public int getStatus() {
		return status;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				if(status == 2) {
					Thread.sleep(25);
					continue;
				}
				//核心任务循环
				mainLoop();
			}catch(InterruptedException e) {
			}
		}
	}

	/**
	 * 核心转换处理循环
	 */
	private void mainLoop() {
		long startTime = System.currentTimeMillis();
		//采集或推流失败次数
		long errIndex = 0;
		long frameIndex = 0;
		//暂停次数
		int pauseNum = 0;
		//正在运行
		if(status == 0) {
			status = 1;
		}
		try {
			for( ;status == 1; frameIndex++) {
				Frame pkt = grabber.grabFrame();
				//暂停状态
				if(pause == 1) {
					pauseNum++;
					continue;
				}
				//采集空包结束
				if(pkt == null) {
					if(errIndex > errStopNum) {//超过三次则终止录制
						break;
					}
					errIndex++;
					continue;
				}
				record.record(pkt);
			}
		}catch (Exception e) {//推流失败
			//到这里表示已经停止了
			status = 2;
			System.err.println("异常导致停止录像，详情：" + e.getMessage());
		}finally {
			status = 2;
			stopRecord();
			System.err.println("录像已停止，持续时长：" + (System.currentTimeMillis() - startTime) + " 毫秒，共录制："
					+ frameIndex + " 帧，遇到的错误数：" + errIndex + " 个, 录制期间共暂停次数：" + pauseNum + " 个。");
		}
	}
	
	/**
	 * 停止录制
	 */
	private void stopRecord() {
		try {
			if(grabber != null) {
				grabber.close();
			}
			if(record != null) {
				record.stop();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 暂停
	 */
	public void pause() {
		pause = 1;
	}
	
	/**
	 * 继续（从暂停中恢复）
	 */
	public void carryOn() {
		pause = 0;
		status = 1;
	}
	
	/**
	 * 结束
	 */
	public void over() {
		status = 2;
	}

}
