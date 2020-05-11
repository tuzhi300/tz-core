package net.kuper.tz.core.cache.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.cache.ConvertAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonAdapter implements ConvertAdapter {

    private ObjectMapper objectMapper;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T extends Serializable> String serialize(T src) {
        try {
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            log.error("Convert object to json data has a error.", e);
        }
        return null;
    }

    @Override
    public <T extends Serializable> T deserialize(String res, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(res, clazz);
        } catch (JsonProcessingException e) {
            log.error("Json data convert to object has a error", e);
        }
        return t;
    }

    @Override
    public <T extends Serializable> List<T> deserializeList(String res, Class<T>... clazzs) {
        List<T> tList = null;
        try {
            JavaType javaType = getCollectionType(ArrayList.class, clazzs);
            tList = objectMapper.readValue(res, javaType);
        } catch (JsonProcessingException e) {
            log.error("Json data convert to collection has a error", e);
        }
        return tList;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
