package com.example.onlineoj.model;


import lombok.Data;

@Data
public class Answer{
    //错误码
    private int error;
    //错误信息
    private String reason;
    //标准输出结果
    private String stdout;
    //标准错误结果
    private String stderr;
    //set和set方法
}
