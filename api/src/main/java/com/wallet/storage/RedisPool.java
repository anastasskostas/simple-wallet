package com.wallet.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class RedisPool {

    private static final Logger logger = LoggerFactory
            .getLogger(RedisPool.class);

    private static JedisPoolConfig jedisPoolConfig;
    private static Jedis jedis;
    private static Integer maxActive = 1000;
    private static Integer maxIdle = 20;
    private static Integer maxWait = 3000;
    private static String hostIp = "localhost";
    private static Integer port = 6379;


    public static Jedis getJedis() {
        if (jedisPoolConfig == null) {
            jedisPoolConfig = new JedisPoolConfig();
            //jedisPoolConfig.setMaxActive(maxActive);
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMaxIdle(maxIdle);
            //jedisPoolConfig.setMaxWait(maxWait);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
        }
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, hostIp, port);
        jedis = jedisPool.getResource();
        return jedis;
    }

    public static void set(String key, String value) {
        try {
            getJedis().set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static String get(String key) {
        try {
            return getJedis().get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public static void sadd(String key, String value) {
        try {
            getJedis().sadd(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static Set<String> smembers(String key) {
        try {
            return getJedis().smembers(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}