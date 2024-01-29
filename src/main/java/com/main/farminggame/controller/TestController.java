package com.main.farminggame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.main.farminggame.connection.Connectiondb;
import com.main.farminggame.test.Person;
import com.main.farminggame.user.User;

import java.sql.Connection;

@RestController
public class TestController {
    @GetMapping("/hello")
    @ResponseBody
    public User sayHello() {
//        return String.format("Hellooo %s!", name);
        Connection con = Connectiondb.connect();
        User user = User.get_user_by_id(con, "USER1");
        return user;
    }
}
