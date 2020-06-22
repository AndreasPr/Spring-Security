package com.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControllerOfTemplate {

    @GetMapping("login")
    public String getLoginTemplate(){
        return "login";
    }

    @GetMapping("employee")
    public String getEmployeeTemplate(){
        return "employee";
    }
}
