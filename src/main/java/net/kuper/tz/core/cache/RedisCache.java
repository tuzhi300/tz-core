package net.kuper.tz.core.cache;

import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.core.utils.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Slf4j
public class RedisCache implements Cache {


    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;


    private RedisTemplate<String, Object> redisTemplate;
    private ConvertAdapter adapter;

    public RedisCache(RedisTemplate<String, Object> redisTemplate, ConvertAdapter adapter) {
        this.redisTemplate = redisTemplate;
        this.adapter = adapter;
    }

    @Override
    public <T extends Serializable> void set(String key, T value) {
        set(key, value, NOT_EXPIRE);
    }

    @Override
    public <T extends Serializable> void set(String key, T value, long expire) {
        if (StringUtils.isEmpty(key)) {
            return;
        } else if (value == null) {
            delete(key);
            return;
        }
        String result = adapter.serialize(value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.opsForValue().set(key, result, expire, TimeUnit.MILLISECONDS);
        } else {
            redisTemplate.opsForValue().set(key, result, expire);
        }
    }

    @Override
    public <T extends Serializable> T get(String key, long expire, Class<T> clazz) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String value = (String) redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
        }
        if (null != value) {
            return adapter.deserialize(value, clazz);
        } else {
            return null;
        }
    }

    @Override
    public <T extends Serializable> List<T> getList(String key, long expire, Class<T>[] clazzs) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String value = (String) redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
        }
        if (null != value) {
            return adapter.deserializeList(value, clazzs);
        } else {
            return null;
        }
    }

    @Override
    public <T extends Serializable> T get(String key, Class<T> clazz) {
        return get(key, NOT_EXPIRE, clazz);
    }

    @Override
    public <T extends Serializable> List<T> getList(String key, Class<T>[] clazzs) {
        return getList(key, NOT_EXPIRE, clazzs);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
