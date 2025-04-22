package com.zjl.keeper.newtest.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author joey
 */
@Slf4j
public class ThreadTest {

    public static void main(String[] args) {

        // 一 继承Thread类
        new  ExThread().start();

        // 二 实现Runnable接口
        Thread thread = new Thread(() -> log.info("Thread 【{}】 is running", Thread.currentThread().getName()));
        thread.start();
        new Thread(new RunThread()).start();

        // 三 实现Callable接口
        FutureTask<String> futureTask = new FutureTask<>(new CallThread());
        try {
            new Thread(futureTask).start();
            String call = futureTask.get();
            log.info("Callable main Thread 【{}】 is running", call);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static class ExThread extends Thread {
        @Override
        public void run() {
            log.info(" extends Thread 【{}】 is running", Thread.currentThread().getName());
        }
    }

        public static class RunThread implements Runnable {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("Runnable Thread 【{}】 is running", Thread.currentThread().getName());
            }
        }

        public static class CallThread implements Callable<String> {

            @Override
            public String call() {
                log.info(" Callable Thread 【{}】 is running", Thread.currentThread().getName());
                return Thread.currentThread().getName() + "-Test";
            }
        }
    }
