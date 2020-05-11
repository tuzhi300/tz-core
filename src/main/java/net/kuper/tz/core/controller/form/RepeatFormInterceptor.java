package net.kuper.tz.core.controller.form;

import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.cache.Cache;
import net.kuper.tz.core.controller.exception.ApiException;
import net.kuper.tz.core.utils.HttpServletUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RepeatFormInterceptor implements MethodInterceptor {

    public static final String EXPRESSION = "@annotation(net.kuper.tz.core.controller.form.RepeatForm)";

    private Cache cache;

    public RepeatFormInterceptor(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        RepeatForm repeatForm = methodInvocation.getMethod().getAnnotation(RepeatForm.class);
        HttpServletRequest request = HttpServletUtils.getHttpServletRequest();
        String sessionId = request.getSession().getId();
        String ip = HttpServletUtils.getIpAddr(request);
        String path = request.getServletPath();
        String key1 = sessionId + path;
//        String key1 = ip + path;
        String res = cache.get(key1, String.class);
        cache.set(key1, key1, repeatForm.interval());
        if (res != null) {
            throw new ApiException(repeatForm.error());
        }
        return methodInvocation.proceed();
    }
}
