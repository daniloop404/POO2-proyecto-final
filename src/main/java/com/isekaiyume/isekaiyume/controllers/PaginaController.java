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

    @GetMapping("/catalogo")
    public String catalogo(){
        return "catalogo";
    }

    @GetMapping("/login")
    public String blog(){
        return "login";
    }

    @GetMapping("/portafolio")
    public String portafolio(){
        return "portafolio";
    }
}
