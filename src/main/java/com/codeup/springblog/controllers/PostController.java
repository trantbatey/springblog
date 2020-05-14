package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/posts")
    public String showPostsIndexPage(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showAnIndividualPost(@PathVariable long id, Model model) {
        Post post = null;
        String errorMessage = "There is no post for ID# " + id + ".";
        Optional<Post> optionalPost = postDao.findById(id);
        if (optionalPost.isPresent()) {
            post = optionalPost.get();
        }
        model.addAttribute("post", post);
        model.addAttribute("errMesg", errorMessage);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String showFormForCreatingAPost(Model model) {
        User user = null;
        Optional<User> optionalUser = userDao.findById(1L);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        Post post = new Post();
        post.setUser(user);
        model.addAttribute("post", post);
        return "/posts/create";
    }

    @GetMapping("/posts/editcreate")
    public String editCreatePost(Model model) {
        User user = null;
        Optional<User> optionalUser = userDao.findById(1L);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        Post post = new Post();
        post.setUser(user);
        model.addAttribute("post", post);
        return "/posts/edit";
    }

    @PostMapping("/posts/create")
    public RedirectView createAPost(@ModelAttribute Post post) {
        postDao.save(post);
        return new RedirectView("/posts/" + post.getId());
    }

    @GetMapping("/posts/{id}/edit")
    public String showFormForEditingAPost(@PathVariable long id, Model model) {
        Post post = null;
        Optional<Post> optionalPost = postDao.findById(id);
        if (optionalPost.isPresent()) {
            post = optionalPost.get();
        } else {
            post = new Post();
        }
        model.addAttribute("post", post);
        return "/posts/edit";
    }

    @PostMapping("/posts/edit")
    public RedirectView editAPost(@ModelAttribute Post post) {
        postDao.save(post);
        return new RedirectView("/posts/" + post.getId());
    }
}
