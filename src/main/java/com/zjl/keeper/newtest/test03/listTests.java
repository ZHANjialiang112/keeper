package com.zjl.keeper.newtest.test03;

import lombok.Data;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class listTests {
    private List list = new ArrayList();

    FutureTask task = new FutureTask<>(new MyCallable());

    Thread thread = new Thread(task);


    

    class MyCallable implements Callable<String>{

        @Override
        public String call() throws Exception {

            return "";
        }
    }
}
