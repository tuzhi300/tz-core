package net.kuper.tz.core.controller.limit;

import java.util.Date;

public interface ILimitRuler {
    /**
     * 验证
     *
     * @param limit
     * @param ip
     * @param session
     * @param userId
     * @param path
     * @param limitId
     * @return
     */
    boolean measure(Limit limit, String ip, String session, Object userId, String path, String limitId);

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
