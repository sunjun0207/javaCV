package cc.sunjun.cv.video.snapshot.web.service;

import cc.sunjun.cv.video.snapshot.web.pojo.ShotParam;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/16 9:30
 * @Description: 视频流快照接口
 */
public interface SnapshotService {

    /**
     * 根据视频流进行截图
     * @param param
     * @return
     */
    Object shot(ShotParam param);

    /**
     * 根据id查询一条视频快照
     * @param id
     * @return
     */
    Object getOne(String id);

    /**
     * 初始化快照
     */
    void init();

}
