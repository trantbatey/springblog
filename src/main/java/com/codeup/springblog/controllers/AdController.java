package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.AdRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/ads/{id}")
    public String showAd(@PathVariable long id, Model model) {
        model.addAttribute("ad", adDao.getOne(id));
        return "ads/show";
    }

    @GetMapping("/ads/{id}/edit")
    public String editAdForm(@PathVariable long id, Model model) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        Ad ad = adDao.getOne(id);
        if (ad.getUser().getId() != user.getId()) {
            return "redirect:/ads/" + ad.getId();
        }
        model.addAttribute("ad", ad);
        return "/ads/edit";
    }

    @PostMapping("/ads/{id}/edit")
    public String editAdWithId(@PathVariable long id, @ModelAttribute Ad ad) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        ad.setId(id);
        ad.setUser(user);
        adDao.save(ad);
        emailService.prepareAndSend(ad, "EDITED Ad: " + ad.getTitle(),
                ad.getTitle() +"\n\n" +
                        ad.getDescription());
        return "redirect:/ads/" + ad.getId();
    }

    @GetMapping("/ads/create")
    public String showCreateForm(Model model) {
        // set user, usually done by using session
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        Ad ad = new Ad();
        ad.setUser(user);
        model.addAttribute("ad", ad);
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String create(@ModelAttribute Ad ad) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        ad.setUser(user);
        adDao.save(ad);
        emailService.prepareAndSend(ad, "CREATED Ad: " + ad.getTitle(),
                ad.getTitle() +"\n\n" +
                        ad.getDescription());
        return "redirect:/ads";
    }

    @PostMapping("/ads/{id}/delete")
    public String deleteAd(@PathVariable long id, Model model) {
        System.out.println(id);
        adDao.deleteById(id);
        return "redirect:/ads";
    }
}
