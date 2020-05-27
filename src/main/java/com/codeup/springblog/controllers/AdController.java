package com.codeup.springblog.controllers;


import com.codeup.springblog.models.Ad;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdController {

    @GetMapping("/ads")
    public String showPostsIndexPage(Model model) {
        List<Ad> ads = new ArrayList<>();
        Ad ad = new Ad();
        ad.setTitle("Car For Sale");
        ad.setDescription("Well Maintained");
        ads.add(ad);
        ad = new Ad();
        ad.setTitle("Help wanted");
        ad.setDescription("Helping to build privacy fence.");
        ads.add(ad);
        model.addAttribute("ads", ads);
        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String showAnIndividualPost(@PathVariable int id, Model model) {
        Ad ad = new Ad();
        ad.setTitle("For Sale");
        ad.setDescription("Get them while they last.");
        model.addAttribute("ad", ad);
        return "ads/show";
    }

    @GetMapping("/ads/create")
    @ResponseBody
    public String showFormForCreatingAPost() {
        return "view the form for creating an ad";
    }

    @PostMapping("/ads/create")
    @ResponseBody
    public String createAPost() {
        return "create a new ad";
    }
}
