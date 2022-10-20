package cc.sunjun.cv.corelib.videoRecord.manager;

import java.util.List;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;
import cc.sunjun.cv.corelib.videoRecord.entity.RecordTask;
import cc.sunjun.cv.corelib.videoRecord.storage.RecordInfoStorage;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 录制任务管理（可以从该处获录制器，并管理这些录制器）
 */

public interface TasksManager {
	
	/**
	 * 设置录像目录
	 * @param recordDir -目录位置，如果为空，则只根据out存放
	 * @return TasksManagerImpl
	 */
	TasksManager setRecordDir(String recordDir);
	
	/**
	 * 设置历史任务信息存储器
	 * @param historyStorage -历史任务信息存储器（如果为空会使用默认）
	 * @return TasksManagerImpl
	 */
	TasksManager setHistoryStorage(RecordInfoStorage historyStorage);
	
	/**
	 * 设置播放地址
	 * @param playUrl
	 * @return
	 */
	TasksManager setPlayUrl(String playUrl);
	
	/**
	 * 获取一个新的录制器，如果为空表示工作线程已满
	 * @param src -视频源
	 * @param out -输出地址（如果recordDir为空，以out为准）
	 * @return
	 * @throws Exception
	 */
	RecordTask createRecord(String src, String out) throws Exception;

	/**
	 * 根据id获取录制任务
	 * @param id
	 * @return
	 */
	RecordTask getRecordTask(Integer id);
	
	/**
	 * 根据id获取录制信息
	 * @param id
	 * @return
	 */
	RecordInfo getRecordInfo(Integer id);
	
	/**
	 * 正在工作的录制任务列表
	 * @return
	 */
	List<RecordTask> list();

	/**
	 * 历史任务列表
	 * @return
	 */
	List<RecordInfo> historyList();
	
	/**
	 * 停止并归还录像任务
	 * @param task
	 * @return 
	 */
	boolean stop(RecordTask task) throws CloneNotSupportedException;
	
	/**
	 * 暂停
	 * @param task
	 * @return
	 */
	boolean pause(RecordTask task);
	
	/**
	 * 继续（从暂停中恢复，停止后无法继续）
	 * @param task
	 * @return
	 */
	boolean carryOn(RecordTask task);
	
	/**
	 * 开始任务
	 * @param task
	 * @return 
	 */
	boolean start(RecordTask task);

}
