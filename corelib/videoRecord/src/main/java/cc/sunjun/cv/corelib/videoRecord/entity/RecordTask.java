package cc.sunjun.cv.corelib.videoRecord.entity;

import cc.sunjun.cv.corelib.videoRecord.record.RecordDetail;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 录像任务管理器
 */

public class RecordTask extends RecordInfo{

	private static final long serialVersionUID = -8883251081940691664L;
    //录像器详情
	private RecordDetail recordDetail;
    //录像器详情状态：1-开启录像、2-停止录像、-1-暂停录像
	private int status;

	public RecordTask(Integer id, String src, String out) {
		super(id, src, out);
	}

	public RecordTask(Integer id, String src, String out, RecordDetail recordDetail) {
		super(id, src, out);
		this.recordDetail = recordDetail;
	}

	public RecordTask(Integer id, String src, String out, long createTime, long endTime) {
		super(id, src, out, createTime, endTime);
	}

	public RecordTask(Integer id, String src, String out, long createTime, long endTime, RecordDetail recordDetail) {
		super(id, src, out, createTime, endTime);
		this.recordDetail = recordDetail;
	}
	
	public RecordDetail getRecordDetail() {
		return recordDetail;
	}

	public void setRecordDetail(RecordDetail recordDetail) {
		this.recordDetail = recordDetail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RecordTask [recordDetail=" + recordDetail + ", status=" + status + ", toString()=" + super.toString() + "]";
	}

}
