package com.ssz.shardingdemo;

import com.ssz.shardingdemo.mapper.TestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ShardingTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    public void testSd(){
        //        Map<String, String> t1 = testMapper.getTest();
        System.out.println("result");
    }
}
