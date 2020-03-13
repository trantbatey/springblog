package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {

    @GetMapping("/add/{n1}/and/{n2}")
    @ResponseBody
    public String add(@PathVariable int n1, @PathVariable int n2) {
        String responseHTML = String.format("%d + %d = %d", n1, n2, n1+n2);
        return responseHTML;
    }

    @GetMapping("/subtract/{n1}/from/{n2}")
    @ResponseBody
    public String subtract(@PathVariable int n1, @PathVariable int n2) {
        String responseHTML = String.format("%d - %d = %d", n2, n1, n2-n1);
        return responseHTML;
    }

    @GetMapping("/multiply/{n1}/and/{n2}")
    @ResponseBody
    public String multiply(@PathVariable int n1, @PathVariable int n2) {
        String responseHTML = String.format("%d * %d = %d", n1, n2, n1*n2);
        return responseHTML;
    }

    @GetMapping("/divide/{n1}/by/{n2}")
    @ResponseBody
    public String divide(@PathVariable int n1, @PathVariable int n2) {
        String responseHTML = String.format("%d / %d = %d", n1, n2, n1/n2);
        return responseHTML;
    }
}
