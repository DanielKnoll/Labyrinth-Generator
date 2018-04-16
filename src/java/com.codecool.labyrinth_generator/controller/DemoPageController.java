package com.codecool.labyrinth_generator.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoPageController {

    @GetMapping(value = "/")
    public String renderMain(){
        return "main";
    }
}
