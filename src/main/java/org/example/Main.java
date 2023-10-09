package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        try {
            String host = "redis-test-7564289b0d6bda14.elb.ap-southeast-1.amazonaws.com";
            int port = 6379;
            boolean ssl = true;
            String password = "0ShjfSP864tRYR242CpU";
            int timeout = 5000;

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(10); //最大活跃连接数
            jedisPoolConfig.setMaxIdle(10);//最大空闲连接数
            jedisPoolConfig.setMinIdle(10);//最小空闲连接数
            jedisPoolConfig.setMaxWait(Duration.ofMillis(5000));//最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException

            JedisPool jedisPool = new JedisPool(
                    jedisPoolConfig,
                    host,
                    port,
                    timeout,
                    password,
                    ssl
            );

            Jedis jedisClient = jedisPool.getResource();
            jedisClient.set("laozheng", "gemen");
            System.out.println(jedisClient.exists("laozheng"));
            System.out.println(jedisClient.get("laozheng"));

            jedisClient.close();
            jedisPool.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}