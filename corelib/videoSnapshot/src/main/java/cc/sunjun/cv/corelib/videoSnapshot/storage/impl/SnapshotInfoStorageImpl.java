package cc.sunjun.cv.corelib.videoSnapshot.storage.impl;

import cc.sunjun.cv.corelib.videoSnapshot.entity.SnapshotInfo;
import cc.sunjun.cv.corelib.videoSnapshot.storage.SnapshotInfoStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/19 9:30
 * @Description: 快照信息存储实现类
 */
public class SnapshotInfoStorageImpl implements SnapshotInfoStorage {

    protected List<SnapshotInfo> historyList = null;

    public SnapshotInfoStorageImpl(int maxSize){
        historyList = new ArrayList<>(maxSize);
    }

    @Override
    public List<SnapshotInfo> list() {
        return historyList;
    }

    @Override
    public boolean save(SnapshotInfo info) {
        return info == null ? false : historyList.add(info);
    }

    @Override
    public SnapshotInfo get(int id) {
        SnapshotInfo info = new SnapshotInfo(id, null, null);
        int ret = Collections.binarySearch(historyList, info, new Comparator<SnapshotInfo>() {
            @Override
            public int compare(SnapshotInfo info1, SnapshotInfo info2) {
                if(info1.getId().equals(info2.getId())){}
                return 0;
            }
        });
        return null;
    }
}
