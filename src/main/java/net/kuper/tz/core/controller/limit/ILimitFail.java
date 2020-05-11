package net.kuper.tz.core.controller.limit;

public interface ILimitFail {

    void onFail(String ip,String session,Object userId,String limitId,String path);

}
