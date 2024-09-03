package com.zjl.keeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy
public class KeeperApplication {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(KeeperApplication.class, args);
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long end = System.currentTimeMillis();
        System.out.println("maxMemory: " + maxMemory / 1024 / 1024 + "M");
        System.out.println("totalMemory: " + totalMemory / 1024 / 1024 + "M");
        System.out.println("freeMemory: " + freeMemory / 1024 / 1024 + "M");
        System.out.println("usedMemory: " + usedMemory / 1024 / 1024 + "M");
        System.out.println("time: " + (end - start) + "ms");
    }

}