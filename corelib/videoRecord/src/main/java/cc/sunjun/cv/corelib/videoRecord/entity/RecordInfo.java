package cc.sunjun.cv.corelib.videoRecord.entity;

import java.io.Serializable;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/9 13:38
 * @Description: 录像信息
 */

public class RecordInfo implements Serializable, Cloneable {

	private static final long serialVersionUID = 2593917989727263187L;

	// id
	private Integer id;
    // 视频源
	private String src;
    // 保存位置
	private String out;
    //播放地址
	private String playUrl;
    // 任务创建时间
	private Long createTime;
    // 任务结束时间
	private Long endTime;

	public RecordInfo() {
		super();
	}

	public RecordInfo(String src, String out) {
		super();
		this.src = src;
		this.out = out;
	}

	public RecordInfo(Integer id, String src, String out) {
		super();
		this.id = id;
		this.src = src;
		this.out = out;
	}

	public RecordInfo(Integer id, String src, String out, long createTime, long endTime) {
		super();
		this.id = id;
		this.src = src;
		this.out = out;
		this.createTime = createTime;
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	@Override
	public RecordInfo clone() throws CloneNotSupportedException {
		return (RecordInfo) super.clone();
	}

	@Override
	public String toString() {
		return "RecordInfo [id=" + id + ", src=" + src + ", out=" + out + ", playUrl=" + playUrl + ", createTime="
				+ createTime + ", endTime=" + endTime + "]";
	}
}
