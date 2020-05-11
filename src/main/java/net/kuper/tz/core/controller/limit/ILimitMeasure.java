package net.kuper.tz.core.controller.limit;

import java.util.Date;

public interface ILimitMeasure {

    /**
     * 获取最近的请求次数
     *
     * @param ip
     * @param session
     * @param sessionBegin
     * @param userId
     * @param userBegin
     * @param limitId
     * @return
     */
    LimitInfo getLimitInfo(String ip, String session, Date sessionBegin, Object userId, Date userBegin, String limitId);

    /**
     * 保存记录
     *
     * @param date
     * @param ip
     * @param session
     * @param userId
     * @param limitId
     * @param path
     * @param description
     */
    void record(Date date, String ip, String session, Object userId, String limitId, String path, String description);


}
