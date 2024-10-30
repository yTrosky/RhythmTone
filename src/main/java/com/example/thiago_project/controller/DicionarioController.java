package com.example.thiago_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DicionarioController {
    @GetMapping("/dicionario")
    public String dicionariopage(){
        return "dicionario";
    }
}