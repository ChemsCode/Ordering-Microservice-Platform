package com.chemseddinesaidi.springbootdemo.controller;

import com.chemseddinesaidi.springbootdemo.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "Hello World!";
    }

//    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @GetMapping("/user")
    public User user() {
        User user = new User();
        user.setId("1");
        user.setName("test");
        user.setEmailId("test@gmail.com");

        return user;
    }

    @GetMapping("/{id}/{id2}")
    public String pathVariable(@PathVariable String id,
                               @PathVariable("id2") String name) {
        return "PathVariable: " + id + " and " + name;
    }

    @GetMapping("/requestParam")
    public String requestParam(@RequestParam String name,
                               @RequestParam(value = "email", required = false, defaultValue = "") String emailId) {
        return "Your name is: " + name + " and your emailId is: " + emailId;
    }
}
