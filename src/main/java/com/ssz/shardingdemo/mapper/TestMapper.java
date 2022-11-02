package com.ssz.shardingdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author ssz
 */
@Repository
public interface TestMapper extends BaseMapper<Map<String,String>> {

    @Select("select * from m limit 1")
    Map<String, String> getTest();


}
