package com.zjl.keeper.newtest.juc;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author joey
 */
@Slf4j
public class ReentrantLockTest {
    public static void main(String[] args) {
        TestReentrantLock testReentrantLock = new TestReentrantLock();
        new Thread(testReentrantLock::getCount, "Thread-1").start();
        new Thread(testReentrantLock::setCount, "Thread-2").start();
    }
}

@Slf4j
class TestReentrantLock {
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private int count = 0;

    public void getCount() {
        lock.lock();
        try {
            log.info("Thread 【{}】 getCount 1: {}", Thread.currentThread().getName(), count);
            condition.await(); // 等待被唤醒(会自动释放锁)
            log.info("Thread 【{}】 getCount 2: {}", Thread.currentThread().getName(), count);
            count++;
            log.info("Thread 【{}】 getCount 3: {}", Thread.currentThread().getName(), count);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void setCount() {
        lock.lock();
        try {
            log.info("Thread 【{}】 setCount 1: {}", Thread.currentThread().getName(), count);
            count = 5;
            log.info("Thread 【{}】 setCount 2: {}", Thread.currentThread().getName(), count);
            condition.signal(); // 唤醒等待的线程
            log.info("Thread 【{}】 setCount 3: {}", Thread.currentThread().getName(), count);
        } finally {
            lock.unlock();
        }
    }
}
