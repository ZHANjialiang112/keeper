package com.zjl.keeper.newtest.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;

/**
 * @author joey
 * @desc  CyclicBarrier 测试
 *
 * CyclicBarrier 是一个同步辅助类，允许一组线程互相等待，直到到达某个公共屏障点。(确保所有线程在特定的点上完成执行，然后一起执行后序任务)
 */
@Slf4j
public class CyclicBarrierThreadTest {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个CyclicBarrier，设置屏障点为3
        CyclicBarrier barrier = new CyclicBarrier(3, () -> log.info("Thread【{}】All threads have reached the barrier, executing the barrier action", Thread.currentThread().getName()));

        for (int i = 0; i < 6; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            new Thread(() -> {
                try { // 模拟线程执行任务
                    log.info("Thread【{}】 is waiting at the barrier", Thread.currentThread().getName());
                    barrier.await(); // 等待其他线程到达屏障点
                    log.info("Thread【{}】 has crossed the barrier", Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread-" + threadNum).start();
        }
    }
}
