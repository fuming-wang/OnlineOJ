package com.example.onlineoj.component;

import com.example.onlineoj.model.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SortTest {


    @Autowired
    private Sort sort;
    @Test
    void getLow() {
        Integer ans = sort.getLow(1,1);
        if(ans==null) System.out.println(11);
        System.out.println(ans);
    }

    @Test
    void getHigh() {
        Integer ans = sort.getHigh(1,1);
        if(ans!=null) System.out.println(ans);
        else System.out.println("null");
    }

    @Test
    void find() {
        Time time = sort.find(1,1);
        System.out.println(time);
    }

    @Test
    void operation() {
    }
}