package com.zjl.keeper.newtest.juc;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @program: keeper
 * @description: 测试 CompletableFuture
 * @author: zjl
 * @date: 2025-04-28 21:01
 **/

@Slf4j
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // CompletableFuture 是一个异步编程的工具类，允许你在 Java 中以非阻塞的方式执行异步任务。
        // 它提供了丰富的 API 来处理异步计算的结果和异常。
        // CompletableFuture 允许你创建一个异步任务，并在任务完成时执行回调操作。
        // 它还支持组合多个异步任务，处理它们之间的依赖关系。

        // CompletableFuture.runAsync() 方法用于异步执行一个没有返回值的任务。
        CompletableFuture.runAsync(() -> {
            // 模拟耗时操作
            log.info("CompletableFuture runAsync is running in thread: {}", Thread.currentThread().getName());
        });
        // CompletableFuture.supplyAsync() 方法用于异步执行一个有返回值的任务。
        CompletableFuture<String> futureString = CompletableFuture.supplyAsync(() -> {
            // 模拟耗时操作
            log.info("CompletableFuture supplyAsync is running in thread: {}", Thread.currentThread().getName());
            return "Hello, CompletableFuture!";
        });
        log.info("CompletableFuture supplyAsync ran return value 【{}】", futureString.get());

        // thenApply() 方法用于在 CompletableFuture 完成后执行一个函数，并返回一个新的 CompletableFuture。
        futureString.thenApply(result -> {
            // 处理结果
            log.info("CompletableFuture thenApply is running in thread: {}", Thread.currentThread().getName());
            return result + " - Processed";
        }).thenAccept(result -> {
            // 处理结果
            log.info("CompletableFuture thenAccept is running in thread: {}", Thread.currentThread().getName());
            log.info("Final result: {}", result);
        }).thenRun(() -> {
            // 处理结果
            log.info("CompletableFuture thenRun is running in thread: {}", Thread.currentThread().getName());
        });

        //allOf 等待多个 CompletableFuture 全部完成  thenRun() 方法用于在 CompletableFuture 完成后执行一个没有返回值的操作。
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> log.info("CompletableFuture allOf is running in thread: 1")),
                CompletableFuture.runAsync(() -> log.info("CompletableFuture allOf is running in thread: 2"))
        ).thenRun(() -> log.info("All tasks completed"));

        // anyOf 等待多个 CompletableFuture 中的任意一个完成
        CompletableFuture.anyOf(
                CompletableFuture.runAsync(() -> {
                    try {
                        // 模拟耗时操作，此处的线程会睡眠10秒 并不会被执行
                        Thread.sleep(10000);
                        log.info("CompletableFuture anyOf is running in thread: 1");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }),
                CompletableFuture.runAsync(() -> log.info("CompletableFuture anyOf is running in thread: 2"))
        ).thenRun(() -> log.info("One tasks completed"));

        // thenCompose() 方法用于在 CompletableFuture 完成后执行一个函数，并返回一个新的 CompletableFuture。
        CompletableFuture<String> thenCompose = CompletableFuture.supplyAsync(() -> "Hello, CompletableFuture!")
                .thenCompose(result -> CompletableFuture.supplyAsync(() -> result + " - thenCompose Processed"));

        // thenCombine() 方法用于将两个 CompletableFuture 的结果组合在一起。
        thenCompose.thenCombine(
                CompletableFuture.supplyAsync(() -> "Hello, World!"),
                (result1, result2) -> {
                    log.info("CompletableFuture thenCombine is running in thread: {}", Thread.currentThread().getName());
                    return result1 + " " + result2;
                }
        ).thenAccept(result -> log.info("Final thenCombine result: {}", result))
                .handle((result, throwable) -> {
                    if (throwable != null) {
                        log.error("Error occurred: {}", throwable.getMessage());
                    } else {
                        log.info("Handle 【{}】", result);
                    }
                    return result;
                });


        // handle() 方法用于处理 CompletableFuture 的结果或异常。
        CompletableFuture.supplyAsync(() -> "Hello, CompletableFuture!").handle((result, throwable) -> {
            if (throwable != null) {
                log.error("Error occurred: {}", throwable.getMessage());
            } else {
                log.info("Handle 【{}】", result);
            }
            return result;
        }).thenAccept(result -> log.info("Final handle result: {}", result));

        // exceptionally() 方法用于处理 CompletableFuture 的异常。
        CompletableFuture.supplyAsync(() -> 1/0).exceptionally(throwable -> {
            log.error("Error occurred: {}", throwable.getMessage());
            return 0;
        }).thenAccept(result -> log.info("Final exceptionally result: {}", result));
    }
}
