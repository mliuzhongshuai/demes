package com.example.demo.redis;

import redis.clients.jedis.Jedis;

/**
 * redis bitMap 应用场景
 *
 * @author liuzhongshuai
 *         Created on 2018/4/26.
 */
public class DemoRedisBitMap {


    /**
     * bitMap 使用，可用于 统计在线人数，过滤等场景
     * 统计签到，点赞等。。。。
     */
    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = new Jedis("localhost");
        String key = "20170212";
        //2017年2月12 两人签到
        jedis.setbit(key, 23, true);
        jedis.setbit(key, 24, true);
        Thread.sleep(2000L);
        //统计bitmap下有多少人签到(相应的二进制位 为1)
        Long bitcount = jedis.bitcount(key);
        System.out.println(bitcount);
        //统计第0-30位的签到数
        Long sesctionCount = jedis.bitcount(key, 0, 30);
        System.out.println(sesctionCount);
    }

}
