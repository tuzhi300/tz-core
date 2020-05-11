package net.kuper.tz.core.controller.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.controller.Res;
import net.kuper.tz.core.properties.TransportProperties;
import net.kuper.tz.core.utils.HttpServletUtils;
import net.kuper.tz.core.utils.InterceptorUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * 系统日志，切面处理类
 *
 * @author lipengjun
 * @date 2017年11月18日 下午13:13:23
 */
@Slf4j
public class LogInterceptor implements MethodInterceptor {


    public static final String EXPRESSION = "@annotation(net.kuper.tz.core.controller.log.Log)";

    private ObjectMapper objectMapper;
    private ILogInterceptorService logSave;
    private TransportProperties properties;

    public LogInterceptor(ObjectMapper objectMapper, ILogInterceptorService logSave, TransportProperties properties) {
        this.objectMapper = objectMapper;
        this.logSave = logSave;
        this.properties = properties;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Log log = methodInvocation.getMethod().getAnnotation(Log.class);
        Object result = null;
        if (log == null) {
            result = methodInvocation.proceed();
        } else {
            String method = InterceptorUtils.getFullMethod(methodInvocation);
            long beginTime = System.currentTimeMillis();

            HttpServletRequest request = HttpServletUtils.getHttpServletRequest();
            LogInfo info = new LogInfo();
            if (request != null) {
                info.setServletPath(request.getServletPath());
                info.setIp(HttpServletUtils.getIpAddr(request));
                info.setUserId(request.getAttribute(properties.getReqAttributeUserIdKey()));
            }
            info.setMethod(method);
            info.setType(log.type().getType());
            info.setOperation(log.value());
            info.setCreateDate(new Date());

            String agentStr = request.getHeader("user-agent");
            // 浏览器
            UserAgent agent = UserAgent.parseUserAgentString(agentStr);
            Browser browser = agent.getBrowser();
            info.setBrowerName(browser.getGroup().getName());
            // 浏览器版本
            Version version = agent.getBrowserVersion();
            if(version!=null){
                info.setBrowerVersion(version.getMajorVersion());
            }
            // 操作系统
            OperatingSystem os = agent.getOperatingSystem();
            if(os!=null){
                info.setOsName(os.getGroup().getName());
            }

            if (log.saveParam()) {
                Object[] paramts = methodInvocation.getArguments();
                if (null != paramts) {
                    List<Object> arguments = new ArrayList<>();
                    for (Object argument : paramts) {
                        if (argument instanceof HttpServletRequest || argument instanceof HttpServletResponse) {
                            continue;
                        }
                        if (argument instanceof MultipartFile) {
                            MultipartFile file = (MultipartFile) argument;
                            Map map = new HashMap();
                            map.put("file-name",file.getName());
                            map.put("file-original-name",file.getOriginalFilename());
                            map.put("file-size",file.getSize());
                            map.put("content-type",file.getContentType());
                            arguments.add(map);
                        } else {
                            arguments.add(argument);
                        }
                    }
                    info.setParams(objectMapper.writeValueAsString(arguments));
                }
            }
            //执行方法
            try {
                result = methodInvocation.proceed();
                if (log.saveResult() && null != result && result instanceof Res) {
                    info.setResult(objectMapper.writeValueAsString(result));
                }
            } catch (Throwable e) {
                info.setError(e.getMessage());
                throw e;
            } finally {
                info.setTime(System.currentTimeMillis() - beginTime);
                logSave.saveLog(info);
            }
        }
        return result;
    }
}
