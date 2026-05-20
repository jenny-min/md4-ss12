package com.example.md4ss12.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("homepage")
public class TestController {
    @GetMapping()
    public String loadHomePage() {
        System.out.println("loadHomePage");
        return "index";
    }
}
