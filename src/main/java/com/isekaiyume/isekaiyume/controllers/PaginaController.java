package com.isekaiyume.isekaiyume.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PaginaController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/registro")
    public String registro(){
        return "registro";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

    @GetMapping("/portafolio")
    public String portafolio(){
        return "portafolio";
    }
}
