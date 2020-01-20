package top.imuster.user.provider.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
@RestControllerAdvice(basePackages = {"top.imuster.user"})
@Slf4j
public class UserExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    @ResponseBody
    public Message serExceptionHandler(UserException exception){
        log.error("user模块出现异常,异常信息为{}",exception.getMessage(), exception);
        return Message.createByError(exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Message usernameNotFoundExceptionHandler(UsernameNotFoundException e){
        log.error("登录失败{}",e.getMessage());
        return Message.createByError("用户名或密码错误");
    }

    @ExceptionHandler(AuthenticationException.class)
    public Message authenticationExceptionHandler(AuthenticationException e){
        log.error("登录异常{}",e.getMessage(),e);
        return Message.createByError("用户名或密码错误");
    }
}
