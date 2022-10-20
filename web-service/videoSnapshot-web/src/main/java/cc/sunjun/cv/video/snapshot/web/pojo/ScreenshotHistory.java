package cc.sunjun.cv.video.snapshot.web.pojo;

import java.util.Date;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/9 13:38
 * @Description: 截图历史记录
 */
public class ScreenshotHistory extends ShotParam {

	// id=名称+时间
	private String id;

	// 图片的base64编码
	private String base64;

	// 截图时间
	private Date shottime;

	public ScreenshotHistory() {
		super();
	}

	public ScreenshotHistory(String id, String src, String name, String output, String base64, Date shottime) {
		super(src, name, output);
		this.id = id;
		this.base64 = base64;
		this.shottime = shottime;
	}

	public ScreenshotHistory(String id, String src, String name, String fmt, String output, String base64,Date shottime) {
		super(src, name, fmt, output);
		this.id = id;
		this.base64 = base64;
		this.shottime = shottime;
	}

	public ScreenshotHistory(ShotParam param) {
		super(param.getSrc(), param.getName(), param.getFmt(), param.getOutput(), param.getWidth(), param.getHeight());
		super.setIsNeedBase64(param.getIsNeedBase64());
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public Date getShottime() {
		return shottime;
	}

	public void setShottime(Date shottime) {
		this.shottime = shottime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ScreenshotHistory [id=" + id + ", base64=" + base64 + ", shottime=" + shottime + ", getSrc()="
				+ getSrc() + ", getFmt()=" + getFmt() + ", getOutput()=" + getOutput() + ", getWidth()=" + getWidth()
				+ ", getHeight()=" + getHeight() + ", getNeedBase64()=" + getIsNeedBase64() + ", getName()=" + getName()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
