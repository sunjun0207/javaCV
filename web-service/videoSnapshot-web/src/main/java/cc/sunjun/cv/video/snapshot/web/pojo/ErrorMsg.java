package cc.sunjun.cv.video.snapshot.web.pojo;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/9 13:38
 * @Description: 响应错误信息实体
 */
public class ErrorMsg {
	private String msg;
	private String code;

	public ErrorMsg() {
		super();
	}

	public ErrorMsg(String msg) {
		super();
		this.msg = msg;
	}


	public ErrorMsg(String msg, String code) {
		super();
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ErrorMsg [msg=" + msg + ", code=" + code + "]";
	}

}
