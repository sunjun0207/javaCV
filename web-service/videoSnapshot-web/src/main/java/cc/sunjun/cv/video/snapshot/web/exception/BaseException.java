package cc.sunjun.cv.video.snapshot.web.exception;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/14 10:36
 * @Description: 基础异常类
 */
@Data
@ToString
public class BaseException extends RuntimeException {

    /**
     * 异常编码
     */
    private int code;

    /**
     * 异常信息
     */
    private String message;

    public BaseException(){

    }

    public BaseException(int code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

}
