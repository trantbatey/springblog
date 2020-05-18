package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.AdRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class AdController {
    private final AdRepository adDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public AdController(AdRepository adDao,
                        UserRepository userDao,
                        EmailService emailService) {
        this.adDao = adDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/ads")
    public String index(Model model) {
        model.addAttribute("ads", adDao.findAll());
        return "ads/index";
    }

    @GetMapping("/ads/create")
    public String showCreateForm(Model model) {
        // set user, usually done by using session
        Ad ad = new Ad();
        User user = userDao.getOne(1L);
        ad.setOwner(user);
        model.addAttribute("ad", ad);
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String create(@ModelAttribute Ad ad) {
        adDao.save(ad);
        emailService.prepareAndSend(ad, "CREATED Ad: " + ad.getTitle(),
                ad.getTitle() +"\n\n" +
                        ad.getDescription());
        return "redirect:/ads";
    }
}
