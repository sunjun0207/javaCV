package cc.sunjun.cv.video.record.web.template;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cc.sunjun.cv.corelib.videoRecord.manager.TasksManager;
import cc.sunjun.cv.corelib.videoRecord.manager.impl.TasksManagerImpl;
import cc.sunjun.cv.corelib.videoRecord.record.RecordDetail;
import cc.sunjun.cv.video.record.web.service.RecordService;
import org.springframework.beans.factory.annotation.Value;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;
import cc.sunjun.cv.corelib.videoRecord.entity.RecordTask;
import cc.sunjun.cv.corelib.videoRecord.storage.RecordInfoStorage;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/9 13:38
 * @Description: 录像服务模板
 */

public abstract class RecordServiceTemplate implements RecordService {
	
	@Value("${record.maxsize}")
	protected Integer maxSize;

	@Value("${record.dir}")
	protected String dir;
	
	@Value("${play.url}")
	protected String playUrl;
	
	protected TasksManager manager = null;
	
	public static volatile int status = 0;

	public static volatile boolean isEnabledFlag = true;

	@Override
	public void switchSegmentRecord(Boolean isEnabled) {
		isEnabledFlag = isEnabled;
	}

	@Override
	public void segmentRecord(String src, String out) throws Exception {
		//支持视频源格式：rtsp/rtmp/flv/hls，录像视频格式：mp4/flv/mkv/avi。
		init();
		while(isEnabledFlag){
			//按照当前时间自动创建"年/月/日/00:00:00 - 23:59:59"文件夹目录：视频文件按照时间进行分片存储，
			String outDir = createDirs(out);
			RecordTask newTask = manager.createRecord(src, outDir);
			RecordDetail recorder = newTask.getRecordDetail();
			Integer currId = newTask.getId();
			RecordTask currTask = manager.getRecordTask(currId);
			currTask.setCreateTime(System.currentTimeMillis());

			//方式一：每隔5秒执行一次新的录像
			newTask.setCreateTime(now());
			newTask.setStatus(TasksManagerImpl.START_STATUS);
			recorder.record(System.currentTimeMillis(), 5 * 1000);

			//方式二：每隔5秒执行一次新的录像
//			record.start();
//			Long startTime = currTask.getCreateTime();
//			while(System.currentTimeMillis() - startTime <= 5 * 1000){
//				//DO NOTHING
//
//			}
			manager.stop(currTask);
		}
	}

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy年MM月dd日-HH时mm分ss秒SSS毫秒");

	/**
	 * 根据xxxx年/xx月/xx日创建文件夹，存放分片视频文件
	 * @param out：视频文件 xxxx年/xx月/xx日/yyyy年MM月dd日-HH时mm分ss秒SSS毫秒(录像).mp4路径
	 * @return
	 */
	private String createDirs(String out){
		//按照xxxx年/xx月/xx日分级创建年月日文件夹，用于保存视频源分片视频
		String curDate = sdf.format(new Date());
		String[] dayArr = curDate.split("-");
		String year = dayArr[0];
		String month = dayArr[1];
		String day = dayArr[2];

		String yearDir = dir + year;
		File yearFile = new File(yearDir);
		if(!yearFile.exists()){
			yearFile.mkdirs();
		}

		String monthDir = yearDir + File.separator + month;
		File monthFile = new File(monthDir);
		if(!monthFile.exists()){
			monthFile.mkdirs();
		}

		String dayDir = monthDir + File.separator + day;
		File dayFile = new File(dayDir);
		if(!dayFile.exists()){
			dayFile.mkdirs();
		}

		out = ((out == null || out.equals("")) ? sdfDate.format(new Date()) : out) + "(录像).mp4";
		String targetDir = year + File.separator + month + File.separator + day + File.separator + out;
		return targetDir;
	}

	@Override
	public boolean pauseById(Integer id) {
		RecordTask task = manager.getRecordTask(id);
		if(task != null){
			manager.pause(task);
			return true;
		}
		return false;
	}

	@Override
	public RecordTask carryOn(Integer id) {
		RecordTask task = manager.getRecordTask(id);
		if(task != null){
			manager.carryOn(task);
			//从暂停中恢复录制，将结束时间置为null
			task.setEndTime(null);
		}
		return task;
	}

	@Override
	public synchronized void init() {
		try {
			if(manager == null) {
                //持久化实现
				RecordInfoStorage cache = initCache();
				manager = new TasksManagerImpl(maxSize).setPlayUrl(playUrl).setRecordDir(dir).setHistoryStorage(cache);
				status = 1;
			}
		} catch (Exception e) {
			status = -1;
		}
	}
	
	/**
	 * 开始录像
	 * @param src
	 * @param out
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@Override
	public RecordTask record(String src, String out) throws Exception {
		init();
		RecordTask task = manager.createRecord(src, out);
		if(task != null) {
			manager.start(task);
		}	
		return task;
	}

	/**
	 * 停止录像
	 * @param id
	 * @return
	 */
	@Override
	public boolean stop(int id) throws CloneNotSupportedException {
		init();
		RecordTask task = manager.getRecordTask(id);
		return manager.stop(task);
	}
	
	/**
	 * 获取录像列表信息
	 * @param isWork -是否工作，true:工作列表，false-历史列表
	 * @return
	 */
	@Override
	public List<?> list(boolean isWork) {
		init();
		List<?> list = isWork ? manager.list() : manager.historyList();
		return list;
	}

	/**
	 * 根据id查询录像信息
	 * @param id：录像视频关联id
	 * @return
	 */
	@Override
	public RecordInfo get(Integer id) {
		RecordInfo info = manager == null ? null : manager.getRecordInfo(id);
		return info;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public static int getStatus() {
		return status;
	}

	/*
	 * 获取当前时间
	 * @return
	 */
	private long now() {
		return System.currentTimeMillis();
	}

}
