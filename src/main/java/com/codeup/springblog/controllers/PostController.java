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
    public String showAnIndividualPost(@PathVariable long id, Model model) {
        if (postDao.findById(id).isEmpty()) {
            model.addAttribute("posts", postDao.findAll());
            model.addAttribute("message", "Post ID# " + id + " Not Found");
            return "posts/index";
        }
        Post post = postDao.getOne(id);
        model.addAttribute("post", post);
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

    @GetMapping("/posts/edit/{id}")
    public String getEditPost(@PathVariable long id, Model model) {
        if (postDao.findById(id).isEmpty()) {
            model.addAttribute("posts", postDao.findAll());
            model.addAttribute("message", "Post ID# " + id + " Not Found");
            return "posts/index";
        }
        Post post = postDao.getOne(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/edit")
    public String postEditPost(@RequestParam(name = "id") long id,
                             @RequestParam(name = "title") String title,
                             @RequestParam(name = "body") String body, Model model) {
        Post post = new Post();
        if (title != null && body != null) {
            post.setId(id);
            post.setTitle(title);
            post.setBody(body);
            postDao.save(post);
        }
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }
}
