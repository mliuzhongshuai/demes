package com.example.demo.thread;


import java.text.ParseException;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程
 *
 * @author liuzhongshuai
 * Created on 2018/4/25.
 */
public class DemesThread {


    /**
     * 测试用多线程并发
     *
     * @throws ParseException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ParseException, InterruptedException {
        //并发10个线程计数
        CountDownLatch countDownLatchThreadCount = new CountDownLatch(10);
        CountDownLatch startLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            //测试线程并发∂
            Thread thread = new Thread(() -> {
                countDownLatchThreadCount.countDown();
                try {
                    startLatch.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        //主线程阻塞等待,所有线程到达点
        countDownLatchThreadCount.await();
        //到达点后 通知所有线程开始业务操作
        startLatch.countDown();
        Thread.sleep(100000000L);
    }
}
