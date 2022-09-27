package cc.sunjun.cv.video.snapshot.web.template;

import cc.sunjun.cv.corelib.videoSnapshot.manager.TasksManager;
import cc.sunjun.cv.corelib.videoSnapshot.snapshot.ScreenshotDetail;
import cc.sunjun.cv.corelib.videoSnapshot.snapshot.impl.FFmpegScreenshotImpl;
import cc.sunjun.cv.video.snapshot.web.cache.ScreenshotCache;
import cc.sunjun.cv.video.snapshot.web.pojo.ErrorMsg;
import cc.sunjun.cv.video.snapshot.web.pojo.ScreenshotHistory;
import cc.sunjun.cv.video.snapshot.web.pojo.ScreenshotInfo;
import cc.sunjun.cv.video.snapshot.web.pojo.ShotParam;
import cc.sunjun.cv.video.snapshot.web.service.SnapshotService;
import cc.sunjun.cv.video.snapshot.web.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/16 10:54
 * @Description: 快照服务模板
 */

public abstract class SnapshotServiceTemplate implements SnapshotService {

    @Value("${snapshot.maxsize}")
    protected Integer maxSize;

    @Value("${snapshot.dir}")
    private String outputDir;

    @Value("${preview.url}")
    protected String playUrl;

    private final ScreenshotDetail shoter = new FFmpegScreenshotImpl();

    protected TasksManager manager = null;

    public static volatile int status = 0;

    public static volatile boolean isEnabledFlag = true;

    @Override
    public Object getOne(String id) {


        return null;
    }

    @Override
    public void init() {
        try{
            if(manager == null){
                //持久化实现

            }
        }catch (Exception e){
            e.printStackTrace();
            status = -1;
        }
    }

    @Override
    public Object shot(ShotParam param) {
        long start = System.currentTimeMillis();
        ErrorMsg errorMsg = new ErrorMsg();
        ScreenshotInfo info = new ScreenshotInfo();
        String url = null, imgUrl = null, fmt = null, base64 = null;

        try {
            if(CommonUtil.isNullOrEmpty((url = param.getSrc()))) {
                errorMsg.setMsg("视频源地址不能为空");
                return errorMsg;
            }
            if(CommonUtil.isNullOrEmpty(param.getFmt())) {
//                errorMsg.setMsg("图片格式不能为空");
//                return errorMsg;
                //如果图片格式为空，则自动默认设置为jpg格式
                param.setFmt("jpg");
            }

            if(CommonUtil.isNullOrEmpty(param.getOutput())){
//                errorMsg.setMsg("图片输出路径不能为空");
//                return errorMsg;
                //如果图片输出路径为空，则自动给配置的输出路径
                param.setOutput(outputDir);
            }

            if(CommonUtil.isNullOrEmpty(param.getName())) {
//                errorMsg.setMsg("图片名称不能为空");
//                return errorMsg;
                //如果图片名称为空，则自动给配置 xxxx年xx月xx日-xx时-xx分-xx秒-xxx毫秒 的名称
                param.setName(sdfDate.format(new Date()));
            }

            if(param.getIsNeedBase64() == null){
                //如果是否生成base64为空，则默认置为false
                param.setIsNeedBase64(false);
            }

            //用户给定的快照输出路径
            imgUrl = param.getOutput() + param.getName() + "." + param.getFmt();
            fmt = param.getFmt();
            info.setSrc(imgUrl);
            //如果需要生成base64编码，则生成图片base64编码
            if(param.getIsNeedBase64() == true) {
                base64 = shoter.shotAndGetBase64(url, imgUrl, fmt);
                info.setBase64(base64);
            }else {
                shoter.shot(url	, imgUrl, fmt);
                long cur = System.currentTimeMillis();
                base64 = shoter.getImgBase64(url, fmt);
                System.err.println("生成base64编码耗时：" + (System.currentTimeMillis() - cur) + " 毫秒");
                info.setBase64(base64);
            }
            save(param,info);
            System.err.println("总耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setMsg("截图失败！");
            return errorMsg;
        }

    }

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy年MM月dd日-HH时mm分ss秒SSS毫秒");

    /**
     * 根据xxxx年/xx月/xx日创建文件夹，存放连续快照文件
     * @param out：快照文件 xxxx年/xx月/xx日/yyyy年MM月dd日-HH时mm分ss秒SSS毫秒(快照).jpg路径
     * @return
     */
    private String createDirs(String out){
        //按照xxxx年/xx月/xx日分级创建年月日文件夹，用于保存视频源连续快照
        String curDate = sdf.format(new Date());
        String[] dayArr = curDate.split("-");
        String year = dayArr[0];
        String month = dayArr[1];
        String day = dayArr[2];

        String yearDir = outputDir + year;
        File yearFile = new File(yearDir);
        if(!yearFile.exists()){
            yearFile.mkdirs();
        }

        String monthDir = yearDir + File.separator + month;
        File monthFile = new File(monthDir);
        if(!monthFile.exists()){
            monthFile.mkdirs();
        }

        String dayDir = monthDir + File.separator + day;
        File dayFile = new File(dayDir);
        if(!dayFile.exists()){
            dayFile.mkdirs();
        }

        out = ((out == null || out.equals("")) ? sdfDate.format(new Date()) : out) + "(快照).jpg";
        String targetDir = year + File.separator + month + File.separator + day + File.separator + out;
        return targetDir;
    }

    private void save(ShotParam param, ScreenshotInfo info) {
        ScreenshotHistory his = new ScreenshotHistory(param);
        his.setBase64(info.getBase64());
        his.setShottime(new Date());
        ScreenshotCache.save(his);
    }
}
