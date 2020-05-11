package net.kuper.tz.core.cache;

import java.io.Serializable;
import java.util.List;

public interface Cache {


    /**
     * 缓存数据
     *
     * @param key
     * @param value
     */
    <T extends Serializable> void set(String key, T value);

    /**
     * 缓存数据
     *
     * @param key
     * @param value
     * @param expire
     */
    <T extends Serializable> void set(String key, T value, long expire);

    /**
     * 获取缓存数据
     *
     * @param key
     * @param clazz
     * @param expire
     * @return
     */
    <T extends Serializable> T get(String key, long expire, Class<T> clazz);

    /**
     * 获取缓存数据列表
     *
     * @param key
     * @param clazzs
     * @param expire
     * @return
     */
    <T extends Serializable> List<T> getList(String key, long expire, Class<T>[] clazzs);

    /**
     * 获取缓存数据
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends Serializable> T get(String key, Class<T> clazz);

    /**
     * 获取缓存数据列表
     *
     * @param key
     * @param clazzs
     * @param <T>
     * @return
     */
    <T extends Serializable> List<T> getList(String key, Class<T>[] clazzs);


    /**
     * 删除缓存数据
     *
     * @param key
     */
    void delete(String key);

}
