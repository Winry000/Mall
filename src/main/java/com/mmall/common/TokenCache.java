package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;


public class TokenCache {

    private static Logger logger = (Logger) LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    private static LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认数据加载实现，如果key没有命中就用这个方法
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        loadingCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = loadingCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        }catch (Exception e) {
            logger.error("local Cache get error", e);
        }
        return null;
    }
}
