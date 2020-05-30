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
import org.springframework.web.bind.annotation.*;

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
    public String showAds(Model model) {
        model.addAttribute("ads", adDao.findAll());
        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String showAd(@PathVariable long id, Model model) {
        if (adDao.findById(id).isEmpty()) {
            model.addAttribute("ads", adDao.findAll());
            model.addAttribute("message", "Ad ID# " + id + " Not Found");
            return "ads/index";
        }
        Ad ad = adDao.getOne(id);
        model.addAttribute("ad", ad);
        return "ads/show";
    }

    @GetMapping("/ads/create")
    public String showCreateForm(Model model) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj == null || !(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        Ad ad = new Ad();
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
        return "redirect:/ads/" + ad.getId();
    }

    @GetMapping("/ads/edit/{id}")
    public String getEditAd(@PathVariable long id, Model model) {
        if (adDao.findById(id).isEmpty()) {
            model.addAttribute("ads", adDao.findAll());
            model.addAttribute("message", "Ad ID# " + id + " Not Found");
            return "ads/index";
        }
        Ad ad = adDao.getOne(id);
        model.addAttribute("ad", ad);
        return "ads/edit";
    }

    @PostMapping("/ads/edit")
    public String postEditAd(@ModelAttribute Ad ad) {
        adDao.save(ad);
        emailService.prepareAndSend(ad, "EDITED Ad: " + ad.getTitle(),
                ad.getTitle() +"\n\n" +
                        ad.getDescription());
        return "redirect:/ads/" + ad.getId();
    }

    @GetMapping("/ads/delete/{id}")
    public String deleteAd(@PathVariable long id, Model model) {
        Ad ad = adDao.getOne(id);
        adDao.deleteById(id);
        emailService.prepareAndSend(ad, "Deleted Ad: " + ad.getTitle(),
                ad.getTitle() +"\n\n" +
                        ad.getDescription());
        return "redirect:/ads";
    }
}
