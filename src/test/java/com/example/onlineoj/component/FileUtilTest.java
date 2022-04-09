package com.example.onlineoj.component;

import com.example.onlineoj.model.Problem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

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

    @Test
    void getProblemFromFile() {
//        int id=3;
//        String path="D:\\Github\\OnlineOJ\\problemMsg\\"+id+"\\";
//        System.out.println(path);
//        File file=new File(path);
//        System.out.println(file.exists());
        Problem problem=fileUtil.getProblemFromFile(3);
        System.out.println(problem);
    }
}