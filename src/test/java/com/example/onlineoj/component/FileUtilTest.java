package com.example.onlineoj.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileUtilTest {

    @Autowired
    private FileUtil fileUtil;
    @Test
    void readFile() {
        String ret=fileUtil.readFile("D:\\Github\\OnlineOJ\\tmp\\test\\abc.txt");
        System.out.println(ret);
    }

    @Test
    void writeFile() {
        String path="D:\\Github\\OnlineOJ\\tmp\\test\\abc.txt";
        fileUtil.writeFile(path,"abcd");
    }
}