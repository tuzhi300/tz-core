package net.kuper.tz.core.utils;

import org.aopalliance.intercept.MethodInvocation;

public final class InterceptorUtils {

    public static String getFullMethod(MethodInvocation methodInvocation) {
        String clazz = methodInvocation.getThis().getClass().getName();
        String method = methodInvocation.getMethod().getName();
        Class[] objects = methodInvocation.getMethod().getParameterTypes();
        String args = "(";
        if (objects != null && objects.length > 0) {
            for (int i = 0; i < objects.length; i++) {
                args += objects[i].getName();
                if (i + 1 < objects.length) {
                    args += ", ";
                }
            }
        }
        args += ")";
        String limitId = clazz + "." + method + args;
        return limitId;
    }
}
