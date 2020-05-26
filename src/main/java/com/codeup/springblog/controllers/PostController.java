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
        Post aPost = new Post();
        aPost.setTitle("Jesse Ventura says he's 'testing the waters' for Green Party bid for president");
        aPost.setBody("Former Minnesota Gov. Jesse Ventura is going to 'test the waters' for a Green Party " +
                " presidential bid.\n\n\"IF I were going to run for president, the GREEN party would be my " +
                "first choice. I've endorsed the party and I'm testing the waters,\" Ventura tweeted Monday morning.");
        postList.add(aPost);
        aPost = new Post();
        aPost.setTitle("'UFO' photos released");
        aPost.setBody("UFO video? Pentagon releases footage of 'unidentified aerial phenomena,' but says it's not " +
                "out of the ordinary.");
        postList.add(aPost);
        model.addAttribute("posts", postList);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showAnIndividualPost(@PathVariable int id, Model model) {
        List<Post> postList = new ArrayList<>();
        Post aPost = new Post();
        aPost.setTitle("Texas plans reopening");
        aPost.setBody("Texas Governor Greg Abbott speaks to the press in Austin on March 29, 2020. On Monday, Abbott " +
                "outline the second phase of reopening businesses in Texas.");
        model.addAttribute("post", aPost);
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
