package cc.sunjun.cv.video.record.web.service.impl;

import cc.sunjun.cv.video.record.web.cache.DefaultCache;
import cc.sunjun.cv.video.record.web.template.RecordServiceTemplate;
import org.springframework.stereotype.Service;

import cc.sunjun.cv.corelib.videoRecord.storage.RecordInfoStorage;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 默认服务实现(自定义持久化实现)
 */

@Service("RecordService")
public class RecordServiceImpl extends RecordServiceTemplate {

	@Override
	public RecordInfoStorage initCache() {
		DefaultCache cache = new DefaultCache(maxSize);
		return cache;
	}

}
