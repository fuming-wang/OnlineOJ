package com.example.onlineoj.dao;

import com.example.onlineoj.model.Problem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProblemMapper {
    int add(Problem problem);
    Problem selectOne(int id);
    List<Problem> selectAll();
    //对内测试接口
    Problem selectOneInDetail(int id);
    int update(Problem problem);
}
