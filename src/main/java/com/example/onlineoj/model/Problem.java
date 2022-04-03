package com.example.onlineoj.model;


import lombok.Data;

@Data
public class Problem {
    private int id;
    private String title;
    private String level;
    private String description;
    private String templateCode;
    private String testCode;

}
