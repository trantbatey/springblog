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
    public String showAnIndividualPost(@PathVariable int id, Model model) {
        List<Post> postList = new ArrayList<>();
        Post post = new Post();
        post.setTitle("Texas plans reopening");
        post.setBody("Texas Governor Greg Abbott speaks to the press in Austin on March 29, 2020. On Monday, Abbott " +
                "outline the second phase of reopening businesses in Texas.");
        User user = new User();
        user.setUsername("Trant");
        user.setEmail("trant@codeup.com");
        post.setUser(user);
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
        Post post = new Post();
        if (title != null && body != null) {
            post.setTitle(title);
            post.setBody(body);
            postDao.save(post);
        }
        return new RedirectView("/posts");
    }
}
