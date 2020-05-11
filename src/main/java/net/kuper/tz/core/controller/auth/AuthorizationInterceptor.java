package net.kuper.tz.core.controller.auth;

import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.controller.ResCode;
import net.kuper.tz.core.controller.exception.ApiException;
import net.kuper.tz.core.properties.TransportProperties;
import net.kuper.tz.core.utils.ReflectUtils;
import net.kuper.tz.core.utils.ShiroUtils;
import net.kuper.tz.core.utils.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private IAuthInterceptorService auth;
    private TransportProperties properties;

    public void setAuth(IAuthInterceptorService auth) {
        this.auth = auth;
    }

    public void setProperties(TransportProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        IgnoreAuth annotation;

        try {
            Object userEntity = ShiroUtils.getUserEntity();
            if (userEntity != null) {
                Object id = ReflectUtils.reflect(userEntity).field(auth.userIdFaild()).get();
                request.setAttribute(this.properties.getReqAttributeUserIdKey(), id);
            }
        } catch (ReflectUtils.ReflectException e) {
            log.error(e.getMessage(), e);
        }

        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }
        if (annotation != null) {
            return true;
        }

        if (auth.whiteServletPath() != null && auth.whiteServletPath().size() > 0) {
            for (String s : auth.whiteServletPath()) {
                String path = request.getServletPath();
                if (path.equals(s) || path.startsWith(s)) {
                    return true;
                }
            }
        }
        if ("/".equals(request.getServletPath()) || request.getServletPath().startsWith("/static")) {
            return true;
        }
        String token = request.getHeader(this.properties.getReqHeaderTokenKey());
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(this.properties.getReqHeaderTokenKey());
        }
        if (StringUtils.isEmpty(token) && !ShiroUtils.isLogin()) {
            //token为空
            throw new ApiException(ResCode.ERROR_SYS_AUTH_UNLOGIN);
        }
        String sign = request.getHeader(this.properties.getReqHeaderSignKey());
        if (StringUtils.isEmpty(sign)) {
            //如果header中不存在sign，则从参数中获取sign
            sign = request.getParameter(this.properties.getReqHeaderSignKey());
        }
        String platform = request.getHeader(this.properties.getReqHeaderPlatformKey());
        if (StringUtils.isEmpty(platform)) {
            platform = request.getParameter(this.properties.getReqHeaderPlatformKey());
        }
        String clientVersion = request.getHeader(this.properties.getReqHeaderVersionKey());
        if (StringUtils.isEmpty(clientVersion)) {
            clientVersion = request.getParameter(this.properties.getReqHeaderVersionKey());
        }
        return true;
    }


}
