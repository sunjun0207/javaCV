package cc.sunjun.cv.video.snapshot.web.controller;

import java.util.Date;
import java.util.List;

import cc.sunjun.cv.video.snapshot.web.service.SnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cc.sunjun.cv.video.snapshot.web.pojo.ErrorMsg;
import cc.sunjun.cv.video.snapshot.web.pojo.ShotParam;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 视频截图（允许跨域请求）
 */
@RestController
@CrossOrigin
public class ScreenshotController {

	@Autowired
	private SnapshotService snapshotService;


	/**
	 * 启用与禁用连续截图功能
	 * @return
	 */
	@RequestMapping("/switchSerialShot")
	public Object switchSerialShot(@RequestParam(required = true) Boolean isEnabled){

		return null;
	}

	/**
	 * 根据视频流进行连续截图
	 * @return
	 */
	@RequestMapping("/serialShot")
	public Object serialShot(@RequestParam(required = true) String videoFlowSrc, @RequestParam(required = true) Boolean isNeedBase64){

		return null;
	}

	/**
	 * 查看所有截图信息
	 * @return
	 */
	@RequestMapping("/list")
	public List<Object> list(){

		return null;
	}

	/**
	 * 根据id查询一条截图信息
	 * @return
	 */
	@RequestMapping("/getOne")
	public Object getOne(@RequestParam(required = true) String id){
		Object object = snapshotService.getOne(id);
		return object;
	}

	/**
	 * 根据视频流进行截图
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/shot", method = RequestMethod.GET)
	public Object shot(ShotParam param) {
		Object object = snapshotService.shot(param);
		return object;
	}

	/**
	 * 帮助说明
	 * @return
	 */
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public Object help() {
		ErrorMsg errorMsg = new ErrorMsg();
		//返回帮助说明信息
		errorMsg.setMsg("请求的参数说明：name-截图名称（必须，可带后缀名，例如eguid.png）；"
				+ "src-视频源地址（必须，rtsp,rtmp,hls,视频文件等等可访问可播放的视频地址）；"
				+ "fmt-保存的图片格式（可选参数，默认保存jpg格式，支持'jpg','png','jpeg','gif','bmp'等等）"
				+ "output-图片保存位置（可选）；needBase64-是否需要base64编码的图像数据（可选）；");
		return errorMsg;
	}

}
