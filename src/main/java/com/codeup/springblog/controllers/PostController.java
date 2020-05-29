package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
        if (postDao.findById(id).isEmpty()) {
            String errorMessage = "There is no post for ID# " + id + ".";
            model.addAttribute("errMesg", errorMessage);
        } else {
            Post post = postDao.getOne(id);
            model.addAttribute("post", post);
        }
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String showFormForCreatingAPost() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String createAPost(@RequestParam(name = "title") String title,
                              @RequestParam(name = "body") String body, Model model) {
        Post post = new Post();
        if (title != null && body != null) {
            post.setTitle(title);
            post.setBody(body);
            User user = userDao.getOne(1l);
            post.setUser(user);
            postDao.save(post);
        }
        return "redirect:/posts/" + post.getId();
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
            User user = userDao.getOne(1l);
            post.setUser(user);
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
