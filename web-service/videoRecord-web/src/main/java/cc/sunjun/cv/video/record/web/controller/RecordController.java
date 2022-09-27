package cc.sunjun.cv.video.record.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cc.sunjun.cv.video.record.web.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.sunjun.cv.corelib.videoRecord.entity.RecordInfo;
import cc.sunjun.cv.corelib.videoRecord.entity.RecordTask;
import cc.sunjun.cv.video.record.web.pojo.ErrorMsg;
import cc.sunjun.cv.video.record.web.service.RecordService;
import cc.sunjun.cv.video.record.web.util.CommonUtil;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 视频源录像
 */

@RestController
@CrossOrigin
public class RecordController {
	
	@Autowired
	private RecordService recorderService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日-hh时mm分ss秒SSS毫秒");

	/**
	 * 开启录像
	 * @return
	 */
	@RequestMapping("/record")
	public ErrorMsg record(@RequestParam(required = true) String src, @RequestParam(required = true) String out) {
		out = ((out == null || out.equals("")) ? sdf.format(new Date()) : out) + "(录像).mp4";
		ErrorMsg errormsg = new ErrorMsg();
		errormsg.setCode("0");
		if(CommonUtil.isAllNullOrEmpty(src, out)) {
			errormsg.setMsg("开启录像失败！");
			return errormsg;
		}
		
		RecordTask rt = null;
		try {
			rt = recorderService.record(src, out);
			if(rt != null) {
				errormsg.setCode("1");
				errormsg.setMsg(rt.getId().toString());
				return errormsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		errormsg.setMsg("开启录像失败！");
		return errormsg;
	}
	
	/**
	 * 停止录像
	 * @return
	 */
	@RequestMapping("/stop")
	public ErrorMsg stop(@RequestParam(required = true) Integer id) {
		ErrorMsg errormsg = new ErrorMsg();
		errormsg.setCode("0");
		try {
			if(recorderService.stop(id)) {
				errormsg.setCode("1");
				errormsg.setMsg("停止录像成功！");
				return errormsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		errormsg.setMsg("停止录像失败！");
		return errormsg;
	}
	
	/**
	 * 查询开启/停止录像列表
	 * @param isWork
	 * @return
	 */
	@RequestMapping("/list")
	public List<?> list(@RequestParam(required = false) String isWork) {
		boolean flag = (isWork != null && isWork.length() > 0 && isWork.equals("true")) ? true : false;
		return recorderService.list(flag);
	}
	
	/**
	 * 根据id查询录像列表
	 * @return
	 */
	@RequestMapping("/get")
	public Object get(@RequestParam(required = true) Integer id) {
		ErrorMsg errormsg = new ErrorMsg();
		errormsg.setCode("0");
		if(CommonUtil.isNull(id)) {
			errormsg.setMsg("id不能为空");
			return errormsg;
		}
		RecordInfo info = recorderService.get(id);
		System.err.println(id + " 为录像视频信息: " + info);
		if(info != null) {
			return info;
		}
		errormsg.setMsg("根据id查询录像视频不存在！");
		return errormsg;
	}

	/**
	 * 根据录像视频id暂停录制
	 * @param id
	 * @return
	 */
	@RequestMapping("/pause")
	public Object pause(@RequestParam(required = true) Integer id){
		ErrorMsg errormsg = new ErrorMsg();
		errormsg.setCode("0");
		if(CommonUtil.isNull(id)) {
			errormsg.setMsg("id不能为空");
			return errormsg;
		}
		List<RecordTask> list = (List<RecordTask>) recorderService.list(true);
		Integer videoId = list.stream().filter(i -> i.getId() != null && i.getId().equals(id)).findAny().get().getId();
		if(videoId != null){
			//对当前id所属的正在录像的视频进行暂停操作
			boolean result = recorderService.pauseById(id);
			if(result){
				errormsg.setCode("1");
				errormsg.setMsg("暂停录像成功！");
				return errormsg;
			}

		}else{
			//如果是录像视频结束了则不能进行暂停，给出提示信息。
			errormsg.setMsg("当前id所属的录像视频已经停止了，不能暂停！");
			return errormsg;
		}
		errormsg.setMsg("根据id暂停录制视频失败！");
		return errormsg;
	}

	/**
	 * 根据录像视频id继续录制（从暂停中恢复）
	 */
	@RequestMapping("/carryOn")
	public ErrorMsg carryOn(@RequestParam(required = true) Integer id){
		ErrorMsg errormsg = new ErrorMsg();
		errormsg.setCode("0");
		if(CommonUtil.isNull(id)) {
			errormsg.setMsg("id不能为空");
			return errormsg;
		}
		List<RecordTask> list = (List<RecordTask>) recorderService.list(true);
		Integer videoId = list.stream().filter(i -> i.getId() != null && i.getId().equals(id)).findAny().get().getId();
		if(videoId != null){
			//对当前id所属处于暂停状态的视频进行继续录制
			RecordTask recordTask = recorderService.carryOn(id);
			errormsg.setMsg("根据录像视频id继续录制成功！");
			return errormsg;
		}else{
			//如果是录像视频结束了则不能进行继续录制，给出提示信息。
			errormsg.setMsg("当前id所属的录像视频已经停止了，不能继续录制！");
			return errormsg;
		}
	}

	/**
	 * 拉取视频流按照日期分片进行保存视频文件
	 */
	@RequestMapping("/segmentRecord")
	public ErrorMsg segmentRecord(@RequestParam(required = true) String src, @RequestParam(required = true) String out){
		ErrorMsg errormsg = new ErrorMsg();
		errormsg.setCode("0");
		if(CommonUtil.isAllNullOrEmpty(src)) {
			errormsg.setMsg("视频源地址不能为空！");
			return errormsg;
		}

		try {
			recorderService.segmentRecord(src, out);
			errormsg.setCode("1");
			errormsg.setMsg("开启分片录像成功！");
			return errormsg;
		} catch (Exception e) {
			e.printStackTrace();
			errormsg.setMsg("开启分片录像失败！");
			return errormsg;
		}
	}

	@RequestMapping("/switchSegmentRecord")
	public ErrorMsg switchSegmentRecord(@RequestParam(required = true) Boolean isEnabled){
		if(isEnabled == null){
			throw new ServiceException(100, "开关设置状态不能为空！");
		}

		recorderService.switchSegmentRecord(isEnabled);
		ErrorMsg errormsg = new ErrorMsg();
		errormsg.setCode("0");
		if(isEnabled == false){
			errormsg.setMsg("关闭分片录像成功！");
		}else{
			errormsg.setMsg("开启分片录像成功！");
		}
		return errormsg;
	}

}
