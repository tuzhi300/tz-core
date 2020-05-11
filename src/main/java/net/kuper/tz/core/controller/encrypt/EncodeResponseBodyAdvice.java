package net.kuper.tz.core.controller.encrypt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.controller.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;

@Slf4j
//@ControllerAdvice("net.kuper.jsb.haoge")
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        SecurityParameter securityParameter = methodParameter.getMethodAnnotation(SecurityParameter.class);
        if (securityParameter == null || securityParameter.outEncode()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            return encryptedBody(body);
        } catch (IOException e) {
            log.error("参数解密失败", e);
            throw new ApiException(e,"参数解密失败!");
        }
    }

    public Object encryptedBody(Object body) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(body);
            byte[] outputData = Encrypt.encrypt(result);
            return new String(outputData);
        } catch (JsonProcessingException e) {
            log.error("参数解密失败", e);
            throw new ApiException(e,"参数解密失败!");
        }
    }


}
