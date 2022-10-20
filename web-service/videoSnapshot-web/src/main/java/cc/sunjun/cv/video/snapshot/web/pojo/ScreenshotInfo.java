package cc.sunjun.cv.video.snapshot.web.pojo;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 截图信息
 */
public class ScreenshotInfo {

	// 截图保存位置
	private String src;

	// 图片的base64编码
	private String base64;

	public ScreenshotInfo() {
		super();
	}

	public ScreenshotInfo(String src, String base64) {
		super();
		this.src = src;
		this.base64 = base64;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	@Override
	public String toString() {
		return "ScreenshotInfo [src=" + src + ", base64=" + base64 + "]";
	}

}
