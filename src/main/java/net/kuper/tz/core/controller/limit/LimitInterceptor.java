package net.kuper.tz.core.controller.limit;

import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.controller.exception.ApiException;
import net.kuper.tz.core.properties.TransportProperties;
import net.kuper.tz.core.utils.HttpServletUtils;
import net.kuper.tz.core.utils.InterceptorUtils;
import net.kuper.tz.core.utils.SpringUtils;
import net.kuper.tz.core.utils.StringUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
public class LimitInterceptor implements MethodInterceptor {

    public static final String EXPRESSION = "@annotation(net.kuper.tz.core.controller.limit.Limit)";

    private ILimitRuler limitRuler;

    private TransportProperties properties;

    public LimitInterceptor(ILimitRuler limitRuler, TransportProperties properties) {
        this.limitRuler = limitRuler;
        this.properties = properties;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Limit limit = methodInvocation.getMethod().getAnnotation(Limit.class);
        String limitId = limit.id();
        if (StringUtils.isEmpty(limitId)) {
            limitId = InterceptorUtils.getFullMethod(methodInvocation);
        }
        ILimitRuler limitRuler;
        ILimitFail limitFail;
        if (!StringUtils.isEmpty(limit.rulerBean())) {
            limitRuler = (ILimitRuler) SpringUtils.getBean(limit.rulerBean());
        } else {
            limitRuler = this.limitRuler;
        }
        if (!StringUtils.isEmpty(limit.failBean())) {
            limitFail = (ILimitFail) SpringUtils.getBean(limit.failBean());
        } else {
            limitFail = null;
        }


        HttpServletRequest request = HttpServletUtils.getHttpServletRequest();
        String ip = HttpServletUtils.getIpAddr(request);
        String session = request.getSession().getId();
        String path = request.getServletPath();
        Object userId = request.getAttribute(properties.getReqAttributeUserIdKey());


        Date currentDate = new Date();
        boolean access = limitRuler.measure(limit, ip, session, userId, path, limitId);
        Object result;
        if (access) {
            try {
                result = methodInvocation.proceed();
            } catch (Throwable e) {
                throw e;
            } finally {
                try {
                    limitRuler.record(currentDate, ip, session, userId, limitId, path, limit.description());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } else {
            if (limitFail != null) {
                try {
                    limitFail.onFail(ip, session, userId, limitId, path);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            throw new ApiException(limit.error());
        }
        return result;
    }
}
