package com.isekaiyume.isekaiyume.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PaginaController {

    @GetMapping("/")
    public String home(){
        return "index";
    }
    @GetMapping("/noticias")
    public String noticias(){
        return "noticias";
    }


}
