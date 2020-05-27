package com.codeup.springblog.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdController {

    @GetMapping("/ads")
    @ResponseBody
    public String showPostsIndexPage() {
        return "ads index page";
    }

    @GetMapping("/ads/{id}")
    @ResponseBody
    public String showAnIndividualPost(@PathVariable int id) {
        return "view an individual ad";
    }

    @GetMapping("/ads/create")
    @ResponseBody
    public String showFormForCreatingAPost() {
        return "view the form for creating an ad";
    }

    @PostMapping("/ads/create")
    @ResponseBody
    public String createAPost() {
        return "create a new ad";
    }
}
