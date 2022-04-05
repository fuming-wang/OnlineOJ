package com.example.onlineoj.model;


import lombok.Data;

@Data
public class Answer{
    //错误码
    private int error;
    //编译错误信息
    private String compileErr;
    //标准输出结果
    private String stdout;
    //标准错误结果
    private String stderr;
}
