package com.example.onlineoj.dao;

import com.example.onlineoj.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByName(String name);
}
