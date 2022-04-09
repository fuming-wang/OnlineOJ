package com.example.onlineoj.controller;


import com.example.onlineoj.dao.UserMapper;
import com.example.onlineoj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/login")
    public void login(String name, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User findUser=userMapper.selectByName(name);
        if(findUser==null||!findUser.getPassword().equals(password)){
            response.sendRedirect("/adminLogin.html");
        }
        request.getSession().setAttribute("user",findUser);
        response.sendRedirect("/test.html");
    }
}
