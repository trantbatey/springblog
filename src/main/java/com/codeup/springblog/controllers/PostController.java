package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao,
                          UserRepository userDao,
                          EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String showPostsIndexPage(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showAnIndividualPost(@PathVariable long id, Model model) {
        Post post = postDao.getOne(id);
        String errorMessage = "There is no post for ID# " + id + ".";
        model.addAttribute("post", post);
        model.addAttribute("errMesg", errorMessage);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String showFormForCreatingAPost(Model model) {
        Post post = new Post();
        User user = userDao.getOne(1L);
        post.setUser(user);
        model.addAttribute("post", post);
        return "/posts/create";
    }

    @GetMapping("/posts/editcreate")
    public String editCreatePost(Model model) {
        User user = userDao.getOne(1L);
        Post post = new Post();
        post.setUser(user);
        model.addAttribute("post", post);
        return "/posts/edit";
    }

    @PostMapping("/posts/create")
    public RedirectView createAPost(@ModelAttribute Post post) {
        postDao.save(post);
        emailService.prepareAndSend(post, "CREATED Post: " + post.getTitle(),
                post.getTitle() +"\n\n" +
                        post.getBody());
        return new RedirectView("/posts/" + post.getId());
    }

    @GetMapping("/posts/{id}/edit")
    public String showFormForEditingAPost(@PathVariable long id, Model model) {
        Post post = postDao.getOne(id);
        model.addAttribute("post", post);
        return "/posts/edit";
    }

    @PostMapping("/posts/edit")
    public RedirectView editAPost(@ModelAttribute Post post) {
        postDao.save(post);
        emailService.prepareAndSend(post, "EDITED post: " + post.getTitle(),
                post.getTitle() +"\n\n" +
                        post.getBody());
        return new RedirectView("/posts/" + post.getId());
    }
}
