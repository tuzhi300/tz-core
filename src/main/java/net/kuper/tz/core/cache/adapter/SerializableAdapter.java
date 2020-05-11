package net.kuper.tz.core.cache.adapter;

import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.cache.ConvertAdapter;
import net.kuper.tz.core.utils.ConvertUtils;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class SerializableAdapter implements ConvertAdapter {

    @Override
    public <T extends Serializable> String serialize(T src) {
        return ConvertUtils.object2String(src);
    }

    @Override
    public <T extends Serializable> T deserialize(String res, Class<T> clazz) {
        return (T) ConvertUtils.string2Object(res);
    }

    @Override
    public <T extends Serializable> List<T> deserializeList(String res, Class<T>... clazzs) {
        return (List<T>) ConvertUtils.string2Object(res);
    }
}
