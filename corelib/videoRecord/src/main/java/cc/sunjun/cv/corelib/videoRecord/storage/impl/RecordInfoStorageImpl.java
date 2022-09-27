package cc.sunjun.cv.corelib.videoRecord.storage.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;
import cc.sunjun.cv.corelib.videoRecord.storage.RecordInfoStorage;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 默认存储实现（根据实际情况实现RecordInfoStorage接口）
 */

public class RecordInfoStorageImpl implements RecordInfoStorage {

	protected List<RecordInfo> historyList = null;

	public RecordInfoStorageImpl(int maxSize) {
		historyList = new ArrayList<>(maxSize);
	}

	@Override
	public List<RecordInfo> list() {
		return historyList;
	}

	@Override
	public synchronized boolean save(RecordInfo info) {
		return info == null ? false : historyList.add(info);
	}

	@Override
	public synchronized RecordInfo get(int id) {
		RecordInfo info = new RecordInfo(id, null, null);
		int ret = Collections.binarySearch(historyList, info, new Comparator<RecordInfo>() {
			@Override
			public int compare(RecordInfo info1, RecordInfo info2) {
				if (info1.getId().equals(info2.getId())) {
					return 0;
				}
				return -1;
			}
		});
		if (ret >= 0) {
			return historyList.get(ret);
		}
		return null;
	}

}
