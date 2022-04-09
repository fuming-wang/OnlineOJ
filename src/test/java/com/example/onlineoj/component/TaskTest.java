package com.example.onlineoj.component;

import com.example.onlineoj.model.Answer;
import com.example.onlineoj.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class TaskTest {
    @Autowired
    private Task task;
    @Test
    void run() {
        Question question=new Question();
        question.setCode("public class Solution{\n" +
                "    public static void main(String[] args){\n" +
                "        System.out.println(\"hello word\");\n" +
                "    }\n" +
                "}");
        Answer answer=task.run(question);
        System.out.println(answer);
    }
}