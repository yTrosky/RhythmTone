package com.example.thiago_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotasController {
    @GetMapping("/notas")
    public String notaspage(){
        return "notas";
    }
}
