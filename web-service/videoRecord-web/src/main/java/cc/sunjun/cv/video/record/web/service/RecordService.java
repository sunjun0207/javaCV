package cc.sunjun.cv.video.record.web.service;

import java.util.List;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;
import cc.sunjun.cv.corelib.videoRecord.entity.RecordTask;
import cc.sunjun.cv.corelib.videoRecord.storage.RecordInfoStorage;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 录像服务接口
 */

public interface RecordService {

	/**
	 * 开启录像
	 * @param src：视频源URL路径，支持flv、hls、m3u8、rtmp、trsp
	 * @param out：录像视频输出路径
	 * @return：返回录像任务对象
	 * @throws Exception
	 */
	RecordTask record(String src, String out) throws Exception;

	/**
	 * 停止录像
	 * @param id：录像信息关联id
	 * @return：返回停止成功或失败的标志结果
	 * @throws CloneNotSupportedException
	 */
	boolean stop(int id) throws CloneNotSupportedException;

	/**
	 * 查看正在录像/结束录像信息
	 * @param isWork：true为正在录像、false为结束录像
	 * @return：返回录像信息
	 */
	List<?> list(boolean isWork);

	/**
	 * 根据id查询录像信息
	 * @param id：录像视频关联id
	 * @return
	 */
	RecordInfo get(Integer id);

	/**
	 * 初始化录像缓存信息
	 * @return：返回录像视频缓存结果
	 */
	RecordInfoStorage initCache();

	/**
	 * 初始化录像
	 */
	void init();

	/**
	 * 暂停录像
	 */
	boolean pauseById(Integer id);

	/**
	 * 继续录像（从暂停中恢复）
	 */
	RecordTask carryOn(Integer id);

	/**
	 * 视频流按照按期进行分片保存
	 */
	void segmentRecord(String src, String out) throws Exception;

	/**
	 * 开关视频流进行分片
	 * @param isEnabled
	 */
	void switchSegmentRecord(Boolean isEnabled);

}
