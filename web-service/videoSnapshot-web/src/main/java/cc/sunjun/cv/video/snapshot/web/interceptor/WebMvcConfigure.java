package cc.sunjun.cv.video.snapshot.web.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/14 18:46
 * @Description: 静态资源拦截器
 */
@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    /**
     * 默认访问的是登录界面
     *
     * springboot 默认的静态资源的值有四个：Default:
     * classpath:/META-INF/resources/,
     * classpath:/resources/,
     * classpath:/static/,默认的静态资源的位置就是resource下面的static文件夹
     * classpath:/public/
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //放行静态资源文件/resources/static/**目录下的所有文件
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}

