package com.isekaiyume.isekaiyume.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PaginaController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/contacto")
    public String contacto(){
        return "contacto";
    }

    @GetMapping("/nosotros")
    public String nosotros(){
        return "nosotros";
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
