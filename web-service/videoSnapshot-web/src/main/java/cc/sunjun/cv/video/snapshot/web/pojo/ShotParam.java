package cc.sunjun.cv.video.snapshot.web.pojo;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 截图参数
 */
public class ShotParam {

	//截图名称（可以包含后缀名）
	private String name;

	//视频地址
	private String src;

	//图片格式
	private String fmt;

	//保存位置
	private String output;

	//宽度
	private Integer width;

	//高度
	private Integer height;

	//是否需要base64编码，true-需要，false-不需要
	private Boolean isNeedBase64;

	public ShotParam() {
		super();
	}
	
	public ShotParam(String src, String name, String output) {
		super();
		this.src = src;
		this.output = output;
	}

	public ShotParam(String src, String name, String fmt, String output) {
		super();
		this.src = src;
		this.fmt = fmt;
		this.output = output;
	}

	public ShotParam(String src, String name, String fmt, String output, Integer width, Integer height) {
		super();
		this.src = src;
		this.fmt = fmt;
		this.output = output;
		this.width = width;
		this.height = height;
	}

	

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getFmt() {
		return fmt;
	}

	public void setFmt(String fmt) {
		this.fmt = fmt;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Boolean getIsNeedBase64() {
		return isNeedBase64;
	}

	public void setIsNeedBase64(Boolean isNeedBase64) {
		this.isNeedBase64 = isNeedBase64;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ShotParam [name=" + name + ", src=" + src + ", fmt=" + fmt + ", output=" + output + ", width=" + width
				+ ", height=" + height + ", isNeedBase64=" + isNeedBase64 + "]";
	}

}
