package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
    @RequestMapping("/mainPage")
    public String mainMainPage() {
        return "/main/mainPage";
    }

    @RequestMapping("/category")
    public String mainCategory() {
        return "/main/category";
    }

}


