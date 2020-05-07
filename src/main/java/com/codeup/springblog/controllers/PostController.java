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
    public String showFormForCreatingAPost() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public RedirectView createAPost(@RequestParam(name = "title") String title,
                              @RequestParam(name = "body") String body, Model model) {
        Post post = new Post();
        if (title != null && body != null) {
            post.setTitle(title);
            post.setBody(body);
            User user = userDao.findById(1l).get();
            post.setUser(user);
            postDao.save(post);
        }
        return new RedirectView("/posts");
    }
}
