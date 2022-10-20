package cc.sunjun.cv.corelib.videoRecord.storage;

import java.util.List;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 录像信息存储接口
 */

public interface RecordInfoStorage {

	List<RecordInfo> list();
	
	boolean save(RecordInfo info);
	
	RecordInfo get(int id);
}
