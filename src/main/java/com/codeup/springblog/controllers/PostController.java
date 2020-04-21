package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String showPostsIndexPage(Model model) {
        List<Post> postList = new ArrayList<>();
        Post post = new Post("1967 Buick Wildcat Convertible", "Mint condition; chery red with black vinyl.");
        postList.add(post);
        post = new Post("Broken fridge", "Just haul it off.");
        postList.add(post);
        model.addAttribute("posts", postList);
        return "/posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showAnIndividualPost(@PathVariable int id, Model model) {
        Post post = new Post("1967 Buick Wildcat Convertible", "Mint condition; chery red with black vinyl.");
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String showFormForCreatingAPost() {
        return "view the form for creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createAPost() {
        return "create a new post";
    }
}
