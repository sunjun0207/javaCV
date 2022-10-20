package cc.sunjun.cv.corelib.videoRecord;

import java.io.IOException;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordTask;
import cc.sunjun.cv.corelib.videoRecord.manager.impl.TasksManagerImpl;
import cc.sunjun.cv.corelib.videoRecord.record.impl.JavaCVRecordImpl;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 录像测试
 */

public class RecordTest {

	//单个测试
	public static void testSingle(cc.sunjun.cv.corelib.videoRecord.manager.TasksManager manager) throws Exception {
		System.err.println("最后一个测试");
		String src = "rtmp://media3.sinovision.net:1935/live/livestream", out = "sunjun.flv";
		RecordTask task = manager.createRecord(src, out);
		manager.start(task);
		
		Thread.sleep(5 * 1000);
		System.err.println("最后一个，当前任务数量：" + manager.list().size() + " 个");
		System.err.println("最后一个，历史任务数量：" + manager.historyList().size() + " 个");
		manager.stop(task);
	}

	//多个同时测试
	public static void main(String[] args) throws Exception {
//		String src = "rtmp://media3.sinovision.net:1935/live/livestream", out = "test";
		cc.sunjun.cv.corelib.videoRecord.manager.TasksManager manager = new TasksManagerImpl(10);
//		RecordTask[] tasks = new RecordTask[20] ;
//		
////		//开始10个
//		for(int i = 1; i < 20; i++) {
//			String file = out + i + ".mp4";
//			RecordTask task = manager.createRecorder(src, file);
//			System.err.println("初始化任务，任务详情：" + task + "，输出位置：" + file);
////			manager.start(task);
//			tasks[i] = task;
//		}
//		
//		for(int i = 1; i < 20; i++) {
//			
//			boolean ret = manager.start(tasks[i]);
//			System.err.println("启动：" + i + (ret ? "，成功" : "，失败"));
//		}
//		
//		System.err.println("当前任务数量：" + manager.list().size() + " 个");
//		System.err.println("历史任务数量："+manager.historylist().size() + " 个");
//		
//		Thread.sleep(5 * 1000);
//		//暂停全部
//		for(RecordTask task : manager.list()) {
//			manager.pause(task);
//		}
//		Thread.sleep(5 * 1000);
////		//恢复全部
////		for(RecordTask task : manager.list()) {
////			manager.carryOn(task);
////		}
//		//停止全部
//		for(RecordTask task : manager.list()) {
//			manager.stop(task);
//		}
		testSingle(manager);//增加一个测试看看历史任务
	
	}
	
	public static void test2() throws org.bytedeco.javacv.FrameGrabber.Exception, IOException, InterruptedException {

			// RecordDetail cv1 = new
			// JavaCVRecordImpl().from("rtmp://media3.sinovision.net:1935/live/livestream").audioParam(2,
			// 128 * 1000, 48 * 1000).
			// to("test.mp4");
			// JavaCVRecordImpl cv2 = new
			// JavaCVRecordImpl().from("rtmp://media3.sinovision.net:1935/live/livestream")
			// .to("test1.mp4");
			// JavaCVRecordImpl cv3 = new
			// JavaCVRecordImpl().from("rtmp://media3.sinovision.net:1935/live/livestream")
			// .to("test2.mp4");
			// JavaCVRecordImpl cv4 = new
			// JavaCVRecordImpl().from("rtmp://media3.sinovision.net:1935/live/livestream")
			// .to("test3.mp4");

			// cvrecord.stream("rtmp://media3.sinovision.net:1935/live/livestream", "sunjun.mp4");
			// JavaCVRecordImpl cvrecord = new
			// JavaCVRecordImpl("rtmp://media3.sinovision.net:1935/live/livestream", "rtmp://106.14.182.20:1935/rtmp/sunjun", 300, 200);
			JavaCVRecordImpl record = new JavaCVRecordImpl();
//			record.from("rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov").to("rtmp://sunjun.cc:1935/rtmp/sunjun");
//			record.forward();
			// cvrecord.stream().forward();//转封装
			record.from("rtmp://media3.sinovision.net:1935/live/livestream").to("sunjun.mp4").start();
			
			// cv1.start();
			// cv2.start();
			// cv3.start();
			// cv4.start();
			 Thread.sleep(5000);
			 record.stop();
			// cv1.pause();
			// Thread.sleep(10000);
			// cv1.carryOn();
			// Thread.sleep(3000);
			// cv1.stop();//停止
			// Thread.sleep(2000);
			// cv1.restart();
			// cv2.start();
			// cv3.start();
			// cv4.start();
			// Thread.sleep(5000);
			// cv1.stop();
			// cv2.stop();
			// cv3.stop();
			// cv4.stop();

	}
}
