package net.kuper.tz.core.controller.auth;

import net.kuper.tz.core.utils.ReflectUtils;
import net.kuper.tz.core.utils.ShiroUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@User注解的方法参数，注入当前登录用户
 * 需要结合AuthorizationAspect同时使用
 */
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private IAuthInterceptorService auth;

    public void setAuth(IAuthInterceptorService auth) {
        this.auth = auth;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter
                .getParameterType()
                .isAssignableFrom(auth.userIdClass()) && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) {
        Object userEntity = ShiroUtils.getSubject().getPrincipal();
        return ReflectUtils.reflect(userEntity).field(auth.userIdFaild()).get();
    }

}
