package net.kuper.tz.core.controller.exception;

//import com.sun.jdi.VMOutOfMemoryException;
import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.controller.Res;
import net.kuper.tz.core.controller.ResCode;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

/**
 * 异常处理器
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    private IExceptionService exceptionService;

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Res handleRRException(ApiException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Res handleDuplicateKeyException(DuplicateKeyException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("数据已存在");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Res handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error("当前数据类型不支持");
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public Res handleHttpMessageNotReadableException(ServletRequestBindingException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error("请填写必要参数");
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public Res handNoMapping(NoHandlerFoundException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error("访问接口不存在");
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Res handleUnauthorizedException(UnauthorizedException ex) {
        log.error(ex.getMessage(), ex);
        return Res.code(ResCode.ERROR_SYS_PERMISSION);
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Res nullPointerExceptionHandler(NullPointerException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("服务器异常：空指针");
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public Res classCastExceptionHandler(ClassCastException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("服务器异常：无法进行类型转换");
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    public Res iOExceptionHandler(IOException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("服务器异常：IO");
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    public Res noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("服务器异常：未知方法");
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Res indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("服务器异常：数组越界");
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Res requestNotReadable(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error("400错误");
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    public Res requestTypeMismatch(TypeMismatchException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error("400错误");
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Res requestMissingServletRequest(MissingServletRequestParameterException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error("400错误");
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Res request405(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error("当前请求方法不支持");
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public Res request406(HttpMediaTypeNotAcceptableException ex) {
        log.error(ex.getMessage(), ex);
        return Res.error("406错误");
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public Res server500(RuntimeException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("空指针异常");
    }

    //栈溢出
//    @ExceptionHandler({StackOverflowError.class})
//    public Res requestStackOverflow(StackOverflowError ex) {
//        VMOutOfMemoryException exception = new VMOutOfMemoryException();
//        exception.setStackTrace(ex.getStackTrace());
//        saveException(exception);
//        log.error(ex.getMessage(), ex);
//        return Res.error("服务器异常：栈溢出");
//    }

    //除数不能为0
    @ExceptionHandler({ArithmeticException.class})
    public Res arithmeticException(ArithmeticException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("服务器异常：除数不能为0");
    }

    //运行时异常
    @Order(999)
    @ExceptionHandler(RuntimeException.class)
    public Res runtimeExceptionHandler(RuntimeException ex) {
        saveException(ex);
        log.error(ex.getMessage(), ex);
        return Res.error("服务器异常：运行时错误");
    }

    //其他错误
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Order(1000)
    public Res handleException(Exception ex) {
        saveException(ex);
        return Res.error("服务器异常");
    }


    private void saveException(Exception ex) {
        try {
            exceptionService.catchError(ex);
        } catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        } finally {
            log.error(ex.getMessage(), ex);
        }
    }
}
