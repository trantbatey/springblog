package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
        Post post = postDao.getOne(id);
        String errorMessage = "There is no post for ID# " + id + ".";
        model.addAttribute("post", post);
        model.addAttribute("errMesg", errorMessage);
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
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        Post post = new Post();
        post.setUser(user);
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

    @GetMapping("/posts/{id}/edit")
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

    @PostMapping("/posts/{id}/edit")
    public String editAPost(@PathVariable long id, @ModelAttribute Post post) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        post.setId(id);
        post.setUser(user);
        postDao.save(post);
        emailService.prepareAndSend(post, "EDITED post: " + post.getTitle(),
                post.getTitle() +"\n\n" +
                        post.getBody());
        return "redirect:/posts/" + post.getId();
    }

    @PostMapping("/posts/{id}/delete")
    public String deleteAd(@PathVariable long id, Model model) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }
}
