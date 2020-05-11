package net.kuper.tz.core.controller.exception;

public interface IExceptionService {

    /**
     * 捕获一个异常
     *
     * @param e
     */
    void catchError(Exception e);
}
