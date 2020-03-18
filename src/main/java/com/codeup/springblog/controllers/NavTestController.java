package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavTestController {

    @GetMapping("/navtest")
    public String showNavTest() {
        return ("navtest");
    }
}
