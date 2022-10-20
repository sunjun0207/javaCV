package cc.sunjun.cv.video.record.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/9 13:38
 * @Description: 视频源录像启动类
 */

@SpringBootApplication
@EnableScheduling //开启定时任务
public class VideoRecordApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(VideoRecordApplication.class, args);
	}
}
