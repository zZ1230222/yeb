package com.code.server.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.code.server.pojo.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testList(){
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        List<Admin> list = adminMapper.selectList(lambdaQueryWrapper);
        list.forEach(System.out::println);
    }
}
