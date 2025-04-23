package com.zjl.keeper.newtest.juc;

import lombok.extern.slf4j.Slf4j;

/**
 * @author joey
 */
@Slf4j
public class SyncThreadTest {
    public static void main(String[] args) {
        TestSync testSync = new TestSync();
        TestSync testSync1 = new TestSync();
        new Thread(()->testSync.buildName("test-hello"), "Thread-4").start();
        new Thread(()->testSync.buildName("test-world"), "Thread-5").start();
        new Thread(()->testSync1.buildName("test-!"), "Thread-6").start();
        log.info("Thread 【{}】count: {}", Thread.currentThread().getName(), TestSync.getCount());
        new Thread(TestSync::increment, "Thread-1").start();
        new Thread(() -> log.info("Thread 【{}】 count: {}", Thread.currentThread().getName(), TestSync.getCount()), "Thread-2").start();
        log.info("Thread 【{}】 count: {}", Thread.currentThread().getName(),TestSync.getCount());
        new Thread(TestSync::increment, "Thread-3").start();
    }
}

@Slf4j
class TestSync {
    private static int count = 0;

    private final String name = "test";

    // synchronized 修饰静态方法 锁是整个对象
    public static synchronized void increment() {
        try {
            Thread.sleep(3000);
            count++;
            log.info("Thread 【{}】 increment count to 【{}】", Thread.currentThread().getName(), count);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized int getCount() {
        return count;
    }

    // synchronized 修饰实例方法 锁是当前对象(实例)
    public void buildName(String hello){
        synchronized (name) {
            log.info("Thread 【{}】 buildName 【{}】", Thread.currentThread().getName(), hello);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
