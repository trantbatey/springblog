package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
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
    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String showPostsIndexPage(Model model) {
        model.addAttribute("posts", postDao.findAll());
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
