package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao,
                          EmailService emailService) {
        this.postDao = postDao;
        this.emailService = emailService;
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
            model.addAttribute("message", "There is no post for ID# " + id + ".");
            return "posts/index";
        }
        Post post = postDao.getOne(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String showFormForCreatingAPost(Model model) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        Post post = new Post();
        post.setUser(user);
        model.addAttribute("post", post);
        return "/posts/create";
    }

    @GetMapping("/posts/editcreate")
    public String editCreatePost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "/posts/edit";
    }

    @PostMapping("/posts/create")
    public String createAPost(@ModelAttribute Post post) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        post.setUser(user);
        postDao.save(post);
        emailService.prepareAndSend(post, "CREATED Post: " + post.getTitle(),
                post.getTitle() +"\n\n" +
                        post.getBody());
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/edit/{id}")
    public String showFormForEditingAPost(@PathVariable long id, Model model) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        Post post = postDao.getOne(id);
        if (post.getUser().getId() != user.getId()) {
            return "redirect:/posts/" + post.getId();
        }
        model.addAttribute("post", post);
        return "/posts/edit";
    }

    @PostMapping("/posts/edit")
    public String editAPost(@ModelAttribute Post post) {
        postDao.save(post);
        emailService.prepareAndSend(post, "EDITED post: " + post.getTitle(),
                post.getTitle() +"\n\n" +
                        post.getBody());
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        if (postDao.findById(id).isPresent()) {
            Post post = postDao.getOne(id);
            postDao.deleteById(id);
            emailService.prepareAndSend(post, "Deleted Post: " + post.getTitle(),
                    post.getTitle() +"\n\n" +
                            post.getBody());
        }
        return "redirect:/posts";
    }
}
