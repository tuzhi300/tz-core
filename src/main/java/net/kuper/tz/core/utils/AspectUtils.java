package net.kuper.tz.core.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AspectUtils {
    private AspectUtils() {// don't instantiate
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    public static <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> clazz) {
        Method method = getMethod(joinPoint);
        return method.getAnnotation(clazz);
    }
}
