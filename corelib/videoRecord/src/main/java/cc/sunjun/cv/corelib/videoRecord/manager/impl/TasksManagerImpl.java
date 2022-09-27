package cc.sunjun.cv.corelib.videoRecord.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;
import cc.sunjun.cv.corelib.videoRecord.entity.RecordTask;
import cc.sunjun.cv.corelib.videoRecord.manager.TasksManager;
import cc.sunjun.cv.corelib.videoRecord.record.RecordDetail;
import cc.sunjun.cv.corelib.videoRecord.record.impl.JavaCVRecordImpl;
import cc.sunjun.cv.corelib.videoRecord.storage.RecordInfoStorage;
import cc.sunjun.cv.corelib.videoRecord.storage.impl.RecordInfoStorageImpl;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 默认任务管理（内置对象池管理）
 */

public class TasksManagerImpl implements TasksManager {

	//开启录像
	public static final int START_STATUS = 1;
	//暂停录像
	public static final int PAUSE_STATUS = -1;
	//停止录像
	public static final int STOP_STATUS = 2;

    // id累加
	protected static volatile int taskIdIndex = 0;
    // 最大任务限制，如果小于0则无限大
	protected volatile int maxSize = -1;
    //历史任务存储
	private RecordInfoStorage historyStorage = null;
    //文件存储目录
	private String recordDir;
    //播放地址
	private String playUrl;

	public RecordInfoStorage getHistoryStorage() {
		return historyStorage;
	}

	@Override
	public TasksManager setHistoryStorage(RecordInfoStorage historyStorage) {
		this.historyStorage = historyStorage;
		return this;
	}
	
	public String getRecordDir() {
		return recordDir;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	@Override
	public TasksManager setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
		return this;
	}

	@Override
	public TasksManager setRecordDir(String recordDir) {
		this.recordDir = recordDir;
		return this;
	}

	// 对象池操作
	// 当前任务池大小
	protected volatile int poolSize = 0;
	// 当前任务池中数量
	protected volatile int workSize = 0;
	// 当前空闲任务数量
	protected volatile int idleSize = 0;
	/** 工作任务池 */
	protected Queue<RecordTask> workPool = null;
	/** 空闲任务池 */
	protected Queue<RecordTask> idlePool = null;

	/**
	 * 初始化
	 * @param maxSize -最大工作任务大小
	 * @param historyStorage -历史任务存储
	 * @throws Exception
	 */
	public TasksManagerImpl(int maxSize, RecordInfoStorage historyStorage) throws Exception {
		super();
		if (maxSize < 1) {
			throw new Exception("maxSize不能空不能小于1");
		}
		this.maxSize = maxSize;
		this.historyStorage = (historyStorage == null ? new RecordInfoStorageImpl(maxSize) : historyStorage);
		this.workPool = new ConcurrentLinkedQueue<>();
		this.idlePool = new ConcurrentLinkedQueue<>();
	}
	
	public TasksManagerImpl(int maxSize) throws Exception {
		this(maxSize, null);
	}



	@Override
	public synchronized RecordTask createRecord(String src, String out) throws Exception {
		RecordTask task = null;
		RecordDetail recorder = null;
		int id = getId();
//		System.err.println("创建时，当前池数量：" + pool_size + ", 空闲数量：" + idle_size + ", 工作数量：" + work_size);
		// 限制任务线程数量，先看有无空闲，再创建新的
		String playUrlLink = (playUrl == null ? out : playUrl + out);
		out = (recordDir == null ? out : recordDir + out);
		if (idleSize > 0) {// 如果有空闲任务，先从空闲任务池中获取
			idleSize--;//空闲池数量减少
			task = idlePool.poll();
			task.setId(id);
			task.setOut(out);
			task.setSrc(src);
			task.setPlayUrl(playUrlLink);
			saveTaskAndInitRecord(task);
			return task;
		}else if (poolSize < maxSize) {// 池中总数量未超出,则新建,若超出，不创建
			recorder = new JavaCVRecordImpl(src, out);
			task = new RecordTask(id, src, out, recorder);
			task.setPlayUrl(playUrlLink);
			saveTaskAndInitRecord(task);
			poolSize++;// 池中活动数量增加
			return task;
		}
		//池中数量已满，且空闲池以空，返回null
		// 超出限制数量，返回空
		return null;
	}
	

	@Override
	public boolean start(RecordTask task) {
		if(task != null) {
			RecordDetail recorder = task.getRecordDetail();
            // 设置开始时间
			task.setCreateTime(now());
            // 状态设为开始
			task.setStatus(START_STATUS);
			recorder.start();
			return true;
		}
		return false;
	}

	@Override
	public boolean pause(RecordTask task) {
		if(task != null) {
			task.setEndTime(now());
            // 状态设为暂停
			task.setStatus(PAUSE_STATUS);
			task.getRecordDetail().pause();
		}
		return false;
	}

	@Override
	public boolean carryOn(RecordTask task) {
		if(task != null) {
			task.getRecordDetail().carryOn();
            // 状态设为开始
			task.setStatus(START_STATUS);
		}
		return false;
	}
	
	@Override
	public boolean stop(RecordTask task) throws CloneNotSupportedException {
		if (task != null && poolSize > 0 && workSize > 0 ) {
			task.getRecordDetail().stop();// 停止录制
			task.setEndTime(now());
			task.setStatus(STOP_STATUS);
			historyStorage.save(task.clone());
			// 工作池中有没有
			if (!workPool.contains(task)) {
				return false;
			}
			// 从工作池中删除，存入空闲池
			if (idlePool.add(task)) {
				idleSize++;
				if (workPool.remove(task)) {
					workSize--;
					System.err.println("归还后，当前线程池中线程数量：" + poolSize + " 个, 空闲线程数量：" + idleSize + " 个, 工作数量：" + workSize + " 个。");
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<RecordInfo> historyList() {
		return historyStorage.list();
	}

	@Override
	public List<RecordTask> list() {
		List<RecordTask> list = new ArrayList<>();
		Iterator<RecordTask> iter = workPool.iterator();
		for( ; iter.hasNext(); ) {
			list.add(iter.next());
		}
		return list;
	}

	@Override
	public RecordTask getRecordTask(Integer id) {
		Iterator<RecordTask> iter = workPool.iterator();
		for( ; iter.hasNext(); ) {
			RecordTask task = iter.next();
			if(task.getId().equals(id)) {
				return task;
			}
		}
		return null;
	}

	@Override
	public RecordInfo getRecordInfo(Integer id) {
		if(id == null || (id - 1) < 0 || id > historyStorage.list().size()){
			return null;
		}
		return historyStorage.list().get(id - 1);
	}

	/*
	 * 保存任务并初始化录制器
	 * @param task
	 * @throws Exception
	 */
	private void saveTaskAndInitRecord(RecordTask task) throws Exception {
		RecordDetail recorder = task.getRecordDetail();
		recorder.from(task.getSrc()).to(task.getOut());//重新设置视频源和输出
		workPool.add(task);
		//由于使用的是池中的引用，所以使用克隆用于保存副本
//		historyStorage.save(task.clone());
        //工作池数量增加
		workSize++;
	}
	
	/*
	 * 获取当前时间
	 * @return
	 */
	private long now() {
		return System.currentTimeMillis();
	}

	/*
	 * 获取自增id
	 * @return
	 */
	private int getId() {
		return ++taskIdIndex;
	}

//	class WorkerThreadTimer{
//		public WorkerThreadTimer(int period){
//			Timer timer = new Timer(false);
//			timer.schedule(new TimerTask() {
//
//				@Override
//				public void run() {
//
//				}
//			},period, period);
//		}
//		public void run() {
//
//		}
//	}
}
