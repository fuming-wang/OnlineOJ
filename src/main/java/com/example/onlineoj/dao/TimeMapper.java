package com.example.onlineoj.dao;


import com.example.onlineoj.model.Time;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TimeMapper {
    Time selectById(int id,int duration);
    Integer selectMin(int id,int duration);
    Integer selectMax(int id,int duration);
    int update(Time time);
    int add(Time time);
}
