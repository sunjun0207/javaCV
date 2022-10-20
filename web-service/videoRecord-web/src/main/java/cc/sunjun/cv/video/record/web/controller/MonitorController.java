package cc.sunjun.cv.video.record.web.controller;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordTask;
import cc.sunjun.cv.video.record.web.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;
import cc.sunjun.cv.video.record.web.service.RecordService;

import java.util.List;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.00
 * @Since: 2022/9/9 13:38
 * @Description: 视频监控录像
 */

@Controller
public class MonitorController {
	
	@Autowired
	RecordService recorderService;
	
	/**
	 * 视频录像列表
	 * @return
	 */
	@RequestMapping(value = "/monitor", method = RequestMethod.GET)
	public String index(Model model, @RequestParam(required = false) Boolean isWork) {
		model.addAttribute("hello", "视频录像历史列表");
		boolean flag = (isWork != null && isWork == true) ? true : false;
		List<RecordTask> list = (List<RecordTask>) recorderService.list(flag);
		model.addAttribute("list", list);
		return "monitor";
	}
	
	/**
	 * 播放录像视频（点播）
	 * @param model
	 * @param id -录像ID
	 * @return
	 */
	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public String play(Model model, @RequestParam(required = true) Integer id) {
		RecordInfo info = recorderService.get(id);
		if(info == null){
			throw new ServiceException(100, "录像视频id不合法！");
		}
		model.addAttribute("record", info);
		return "play";
	}
	
	/**
	 * 测试视频流录像
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testRecord", method = RequestMethod.GET)
	public String test(Model model) {
		model.addAttribute("hello", "测试视频录像");
		return "testRecord";
	}
}
