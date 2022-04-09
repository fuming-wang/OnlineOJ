package com.example.onlineoj.dao;

import com.example.onlineoj.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectByName() {
        String name="wfm";
        User user=userMapper.selectByName(name);
        System.out.println(user);
    }
}