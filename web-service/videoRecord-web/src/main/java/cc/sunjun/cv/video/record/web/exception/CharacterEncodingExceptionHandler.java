package cc.sunjun.cv.video.record.web.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Sun Jun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/15 17:14
 * @Description: 自定义编码格式
 */
@Component
public class CharacterEncodingExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        response.setCharacterEncoding("utf-8");
        return null;
    }
}
