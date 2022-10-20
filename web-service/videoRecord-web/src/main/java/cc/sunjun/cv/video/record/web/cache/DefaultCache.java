package cc.sunjun.cv.video.record.web.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;
import cc.sunjun.cv.corelib.videoRecord.storage.RecordInfoStorage;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 自定义持久化
 */

public class DefaultCache implements RecordInfoStorage {

	protected List<RecordInfo> historylist = null;

	public DefaultCache(int maxSize) {
		historylist = new ArrayList<>(maxSize);
	}

	@Override
	public synchronized List<RecordInfo> list() {
		return historylist;
	}

	@Override
	public synchronized boolean save(RecordInfo info) {
		return info == null ? false : historylist.add(info);
	}

	@Override
	public synchronized RecordInfo get(int id) {
		RecordInfo info = new RecordInfo(id, null, null);

		int ret = Collections.binarySearch(historylist, info, new Comparator<RecordInfo>() {
			@Override
			public int compare(RecordInfo o1, RecordInfo o2) {
				if (o1.getId().equals(o2.getId())) {
					return 0;
				}
				return -1;
			}
		});

		if (ret >= 0) {
			return historylist.get(ret);
		}

		return null;
	}

}
