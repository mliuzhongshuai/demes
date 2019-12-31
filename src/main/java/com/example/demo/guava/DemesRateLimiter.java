package com.example.demo.guava;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

/**
 * 令牌桶
 * @author liuzhongshuai
 *         Created on 2018/4/25.
 */
public class DemesRateLimiter {

    /**
     * 测试guava 令牌桶算法
     * 该算法可用户api接口限流
     * 允许有并发高峰
     */
    public static void main(String[] args) {
        //初始化 通容量 为100 的 ps:每秒生成100个令牌
        RateLimiter rateLimiter = RateLimiter.create(100);
        //尝试取令牌，超时时间1秒 ，如果1秒取不到则立即返回，不会阻塞，只有判断取得到的时候 才有可能发生阻塞。
        //默认取出一个
        rateLimiter.tryAcquire(1, TimeUnit.SECONDS);
        //尝试取出10个
        rateLimiter.tryAcquire(10, 1, TimeUnit.SECONDS);
    }
}
