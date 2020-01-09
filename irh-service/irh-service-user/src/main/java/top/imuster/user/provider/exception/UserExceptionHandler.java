package top.imuster.user.provider.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: UserExceptionHandler
 * @Description: user模块的异常处理类
 * @author: hmr
 * @date: 2019/12/19 20:14
 */
@RestControllerAdvice(basePackages = {"top.imuster.user.provider.web"})
public class UserExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    @ResponseBody
    public Message serExceptionHandler(UserException exception){
        return Message.createByError(exception.getMessage());
    }
}
