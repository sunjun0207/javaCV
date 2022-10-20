package cc.sunjun.cv.corelib.videoSnapshot.storage;

import cc.sunjun.cv.corelib.videoSnapshot.entity.SnapshotInfo;

import java.util.List;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/19 9:49
 * @Description: 快照信息存储接口
 */

public interface SnapshotInfoStorage {

    List<SnapshotInfo> list();

    boolean save(SnapshotInfo info);

    SnapshotInfo get(int id);

}
