package com.zjl.keeper.newtest.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @author joey
 * @desc Semaphore 信号量测试
 */
@Slf4j
public class SemaphoreThreadTest {
    public static void main(String[] args) {

        // 创建一个信号量，允许3线程同时访问(即3个许可) 限制同事访问特定资源的线程数 未获取许可的线程会阻塞
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.info("Thread 【{}】 acquired a permit",Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Thread 【{}】 interrupted", Thread.currentThread().getName());
                } finally {
                    log.info("Thread 【{}】 released a permit",Thread.currentThread().getName());
                    semaphore.release();
                }
            }, "Thread-" + i).start();
        }
    }
}
