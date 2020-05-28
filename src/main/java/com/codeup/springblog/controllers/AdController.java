package com.codeup.springblog.controllers;


import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.AdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdController {
    private final AdRepository adDao;

    public AdController(AdRepository adDao) {
        this.adDao = adDao;
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
    public String getCreateAd() {
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String postCreateAd(@RequestParam(name = "title") String title,
                               @RequestParam(name = "description") String description, Model model) {
        Ad ad = new Ad();
        if (title != null && description != null) {
            ad.setTitle(title);
            ad.setDescription(description);
            adDao.save(ad);
        }
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
        if (title != null && description != null) {
            ad.setId(id);
            ad.setTitle(title);
            ad.setDescription(description);
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
