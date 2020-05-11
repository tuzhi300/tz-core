package net.kuper.tz.core.controller.encrypt;

import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.controller.exception.ApiException;
import net.kuper.tz.core.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

@Slf4j
//@ControllerAdvice("net.kuper.jsb.haoge")
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        SecurityParameter securityParameter = methodParameter.getMethodAnnotation(SecurityParameter.class);
        if (securityParameter == null || securityParameter.inDecode()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        try {
            return new MyHttpInputMessage(httpInputMessage);
        } catch (IOException e) {
            log.error("参数解密失败", e);
            throw new ApiException("参数解密失败!");
        }
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }


    //这里实现了HttpInputMessage 封装一个自己的HttpInputMessage
    class MyHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;

        public MyHttpInputMessage(HttpInputMessage httpInputMessage) throws IOException {
            this.headers = httpInputMessage.getHeaders();
            // 解密操作
            this.body = decryptBody(httpInputMessage.getBody());
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

    /**
     * 对流进行解密操作
     *
     * @param in
     * @return
     */
    private InputStream decryptBody(InputStream in) throws IOException {
        try {
            // 获取 json 字符串
            String bodyStr = IOUtils.toString(in, "UTF-8");
            if (StringUtils.isEmpty(bodyStr)) {
                throw new ApiException("没有收到任何参数!");
            }
            byte[] decryptBody = Encrypt.decrypt(bodyStr);
            return IOUtils.toInputStream(new String(decryptBody), "UTF-8");
        } catch (IOException e) {
            log.error("参数解密失败", e);
            throw new ApiException("参数解密失败!");
        }
    }
}
