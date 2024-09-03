package com.zjl.keeper.newtest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author joey
 * @description 测试新版本相关功能
 */
public class ChaeacterizationTest {

    String content = """
            {
                "hello": "test",
                "hello": "test1"
            }
            """;

    public static void main(String[] args) {
//        testCase();
//        testVirtualThread();
        testStream();
    }

    /**
     * 测试新版 switch-case表达式
     */
    public static void testCase(){
        for (Object i : Arrays.asList(1, "2", 3, 4, 5, 6, 7,null)){
            var s =   switch (i) {
                case null: yield  "null";
                case Integer j: yield  j;
                case String X : yield  X;
//                case 1, 2, 3 :yield "123";
//                case 4, 5, 6 : yield  "456";
                default: yield "789";
            };
            System.out.printf("switch:%s\n", s);
        }
        ChaeacterizationTest chaeacterizationTest = new ChaeacterizationTest();
        System.out.println(chaeacterizationTest.content);
    }

    /**
     * 测试新版虚拟线程
     */
    public static void testVirtualThread(){
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(1000);
                    return i;
                });
            });
        } // executor.close() 会被自动调用
        // 提交了 10 万个虚拟线程，每个线程休眠 1 秒钟，1秒左右完成
        System.out.println("耗时:" + (System.currentTimeMillis() - start)+"ms");
    }

    /**
     * debug 测试 stream 流
     */
    public static void testStream() {
        List<DataTest> list = new ArrayList<>();
        for (int i = 18; i < 25; i++) {
            DataTest dataTest = new DataTest("zjl" + i, i);
            list.add(dataTest);
        }
        list.add(new DataTest("", 25));
        list.add(new DataTest("test", 13));
        list.add(new DataTest("test", 19));
        list.add(new DataTest("test", 24));

        Set<DataTest> collect = list.stream()
                .filter(s -> StringUtils.isNotBlank(s.getName())).filter(s -> s.getAge() > 18)
                .peek(s -> s.setName(s.getName().toUpperCase())).filter(s -> s.getAge() > 22).collect(Collectors.toSet());
        System.out.println(collect);
    }
}

@Data
@AllArgsConstructor
@NonNull
class DataTest{
    private String name;
    private int age;
}
