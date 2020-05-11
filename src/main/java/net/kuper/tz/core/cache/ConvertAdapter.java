package net.kuper.tz.core.cache;

import java.io.Serializable;
import java.util.List;

public interface ConvertAdapter {

    /**
     * 序列化
     *
     * @param src
     * @param <T>
     * @return
     */
    <T extends Serializable> String serialize(T src);

    /**
     * 反序列化
     *
     * @param res
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends Serializable> T deserialize(String res, Class<T> clazz);

    /**
     * 反序列化
     *
     * @param res
     * @param clazzs
     * @param <T>
     * @return
     */
    <T extends Serializable> List<T> deserializeList(String res, Class<T>... clazzs);
}
