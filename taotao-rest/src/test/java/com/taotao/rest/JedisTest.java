package com.taotao.rest;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/2/21 20:06
 */
public class JedisTest {

    @Test
    public void springJedisCluster() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisCluster jedisCluster = (JedisCluster) context.getBean("redisClient");
        jedisCluster.set("name","wangyichao");
        System.out.println(jedisCluster.get("name"));
        jedisCluster.close();
    }
}
