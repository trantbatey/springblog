package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IncludeExampleController {

    @GetMapping("/include")
    public String showEntirePageWithThInclude() {
        return "include";
    }
}
