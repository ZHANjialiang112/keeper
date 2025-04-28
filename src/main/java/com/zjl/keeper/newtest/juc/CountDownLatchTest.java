package com.zjl.keeper.newtest.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author joey
 * @desc CountDownLatch 测试
 * @date 2025/4/15
 * CountDownLatch 是一个同步辅助类，允许一个或多个线程等待直到在其他线程中执行的一组操作完成。
 * 它的主要作用是使一个线程等待其他线程完成某些操作后再继续执行。
 * 例如：在一个多线程环境中，主线程可以使用 CountDownLatch 等待所有子线程完成任务后再继续执行。
 * 和 CyclicBarrier 相比，不能够重复使用
 */
@Slf4j
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个 CountDownLatch，初始计数为3
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            final int threadNum = i;
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    log.info("Thread【 {}】 is finished",  Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown(); // 每个线程完成后调用 countDown() 方法，计数减1
                }
            }, "Thread-" + threadNum).start();
        }

        latch.await(); // 主线程等待，直到计数为0
        log.info("All threads are finished, main thread continues");
    }
}
