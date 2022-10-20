package cc.sunjun.cv.corelib.videoSnapshot.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/16 18:28
 * @Description: 快照信息
 */
@Data
@ToString
public class SnapshotInfo implements Serializable, Cloneable{

    private static final long serialVersionUID = 2593917989727263187L;

    // id
    private Integer id;
    // 视频源
    private String src;
    // 保存位置
    private String out;
    //预览地址
    private String previewUrl;
    // 任务创建时间
    private Long createTime;
    // 任务结束时间
    private Long endTime;

    public SnapshotInfo(){
        super();
    }

    public SnapshotInfo(Integer id, String src, String out){
        super();
        this.id = id;
        this.src = src;
        this.out = out;
    }

    public SnapshotInfo(String src, String out){
        super();
        this.src = src;
        this.out = out;
    }

    public SnapshotInfo(Integer id, String src, String out, Long createTime, Long endTime){
        super();
        this.id = id;
        this.src = src;
        this.out = out;
        this.createTime = createTime;
        this.endTime = endTime;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
