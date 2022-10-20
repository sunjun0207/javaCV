package cc.sunjun.cv.corelib.videoSnapshot.entity;


import cc.sunjun.cv.corelib.videoSnapshot.snapshot.ScreenshotDetail;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/16 18:26
 * @Description: 快照任务管理器
 */
@Data
@ToString
public class SnapshotTask extends SnapshotInfo {

    private static final long serialVersionUID = -8883251563289691664L;
    //快照器详情
    private ScreenshotDetail screenshotDetail;
    //快照器详情状态：1-开启快照、2-停止快照、-1-暂停快照
    private int status;

    public SnapshotTask(Integer id, String src, String out){
        super(id, src, out);
    }

    public SnapshotTask(Integer id, String src, String out, ScreenshotDetail screenshotDetail){
        super(id, src, out);
        this.screenshotDetail = screenshotDetail;
    }

    public SnapshotTask(Integer id, String src, String out, long createTime, long endTime){
        super(id, src, out, createTime, endTime);
    }

    public SnapshotTask(Integer id, String src, String out, long createTime, long endTime, ScreenshotDetail screenshotDetail){
        super(id, src, out, createTime, endTime);
        this.screenshotDetail = screenshotDetail;
    }

}
