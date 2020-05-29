package com.codeup.springblog.controllers;


import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.AdRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdController {
    private final AdRepository adDao;
    private final UserRepository userDao;

    public AdController(AdRepository adDao,
                        UserRepository userDao) {
        this.adDao = adDao;
        this.userDao = userDao;
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
        // set user, usually done by using session
        Ad ad = new Ad();
        User user = userDao.getOne(1l);
        ad.setOwner(user);
        model.addAttribute("ad", ad);
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String postCreateAd(@ModelAttribute Ad ad) {
        adDao.save(ad);
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
    public String postEditAd(@RequestParam(name = "id") long id,
                             @RequestParam(name = "title") String title,
                             @RequestParam(name = "description") String description, Model model) {
        Ad ad = new Ad();
        User user = userDao.getOne(1l);
        if (title != null && description != null) {
            ad.setId(id);
            ad.setTitle(title);
            ad.setDescription(description);
            ad.setOwner(user);
            adDao.save(ad);
        }
        return "redirect:/ads/" + ad.getId();
    }

    @GetMapping("/ads/delete/{id}")
    public String deleteAd(@PathVariable long id, Model model) {
        adDao.deleteById(id);
        return "redirect:/ads";
    }
}
