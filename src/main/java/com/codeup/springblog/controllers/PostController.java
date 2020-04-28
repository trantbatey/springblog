package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    public String showFormForCreatingAPost() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public RedirectView createAPost(@RequestParam(name = "title") String title,
                              @RequestParam(name = "body") String body, Model model) {
        Post aPost = new Post();
        if (title != null && body != null) {
            aPost.setTitle(title);
            aPost.setBody(body);
            postDao.save(aPost);
        }
        return new RedirectView("/posts");
    }
}
