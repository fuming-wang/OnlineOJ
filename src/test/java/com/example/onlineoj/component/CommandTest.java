package com.example.onlineoj.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Autowired
    private Command command;
    @Test
    void run() {
        int ret=command.run("javac","stdout.txt","stderr.txt");
        System.out.println(ret);
    }
}