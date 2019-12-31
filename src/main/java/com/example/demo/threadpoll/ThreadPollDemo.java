package com.example.demo.threadpoll;

import javafx.concurrent.ScheduledService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author liuzhongshuai
 * @description er
 * @date 2019-12-21 10:30
 **/
public class ThreadPollDemo {

    public static void main(String[] args) {

        //创建线程池不要用Executors 工厂类去创建，因为它会屏蔽一些参数，可能导致异常的发生，除了 ScheduledThreadPool 线程池 其他几个线程池都可以用 ThreadPoolExecutor 去实现

        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(4, r -> new Thread("asdf"), (r, e) -> System.out.println("触发拒绝task执行！"));
        //核心线程预热 提前初始化
        scheduledExecutorService.prestartCoreThread();

        //其他类型的线程池都可以通过这种方式进行获取
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(1,20,1000,TimeUnit.SECONDS ,new LinkedBlockingDeque(2),r -> new Thread("asdf"), (r, e) -> System.out.println("触发拒绝task执行！"));

        while (true) {
            System.out.println("当前task 数量:"+threadPoolExecutor.getTaskCount());
            threadPoolExecutor.execute(() -> {
                System.out.println("开始执行线程，并睡眠！");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }

            });
        }

    }
}
