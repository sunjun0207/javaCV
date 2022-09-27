package cc.sunjun.cv.video.record.web.exception;

/**
 * @Author: SunJun
 * @Email: sunjun0207@163.com
 * @Version: 1.0
 * @Since: 2022/9/14 10:45
 * @Description: 业务层异常信息
 */
public class ServiceException extends BaseException{

    public ServiceException(){

    }

    public ServiceException(int code, String message){
        super(code, message);
    }


}
