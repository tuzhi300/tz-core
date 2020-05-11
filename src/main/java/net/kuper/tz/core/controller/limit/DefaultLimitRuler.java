package net.kuper.tz.core.controller.limit;

import net.kuper.tz.core.controller.exception.ApiException;

import java.util.Date;

public class DefaultLimitRuler implements ILimitRuler {

    private ILimitMeasure limitMeasure;

    public DefaultLimitRuler(ILimitMeasure limitMeasure) {
        this.limitMeasure = limitMeasure;
    }

    private boolean sessionAccept(Limit limit, LimitInfo limitInfo) {
        return limit.sessionCount() > limitInfo.getSessionCount();
    }

    private boolean userAccept(Limit limit, LimitInfo limitInfo) {
        return limit.userCount() > limitInfo.getUserCount();
    }

    @Override
    public boolean measure(Limit limit, String ip, String session, Object userId, String path, String limitId) {
        Date sessionDate = new Date(System.currentTimeMillis() - limit.sessionTime());
        Date userDate = new Date(System.currentTimeMillis() - limit.userTime());
        if (userId == null && (limit.type() == LimitType.USER || limit.type() == LimitType.SESSION_AND_USER || limit.type() == LimitType.SESSION_OR_USER)) {
            throw new ApiException("Parameter userId is null .");
        }
        LimitInfo info = limitMeasure.getLimitInfo(ip, session, sessionDate, userId, userDate, limitId);
        boolean access = false;
        switch (limit.type()) {
            case SESSION:
                access = sessionAccept(limit, info);
                break;
            case USER:
                access = userAccept(limit, info);
                break;
            case SESSION_AND_USER:
                access = sessionAccept(limit, info) && userAccept(limit, info);
                break;
            case SESSION_OR_USER:
                access = sessionAccept(limit, info) || userAccept(limit, info);
                break;
        }
        return access;
    }

    @Override
    public void record(Date date, String ip, String session, Object userId, String limitId, String path, String description) {
        limitMeasure.record(date, ip, session, userId, limitId, path, description);
    }
}
