package cc.sunjun.cv.video.snapshot.web.controller;

import cc.sunjun.cv.video.snapshot.web.cache.ScreenshotCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cc.sunjun.cv.video.snapshot.web.pojo.ScreenshotHistory;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 视频监控截图
 */
@Controller
public class MonitorController {

	/**
	 * 查看视频流截图列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/monitor", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("hello", "视频截图记录列表");
		model.addAttribute("hislist", ScreenshotCache.getHistoryList());
		return "monitor";
	}
	
	/**
	 * 图片预览
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewImage", method = RequestMethod.GET)
	public String view(Model model, @RequestParam(required=true) String id) {
		model.addAttribute("hello", "截图预览");
		ScreenshotHistory his = ScreenshotCache.get(id);
		model.addAttribute("his", his);
		return "viewImage";
	}

	/**
	 * 测试视频流截图
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testImgshot", method = RequestMethod.GET)
	public String testShot(Model model) {
		model.addAttribute("hello", "视频截图测试");
		return "testImgshot";
	}
}
