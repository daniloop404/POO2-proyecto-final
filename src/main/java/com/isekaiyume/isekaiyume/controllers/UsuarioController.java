package com.isekaiyume.isekaiyume.controllers;

import com.isekaiyume.isekaiyume.entidades.Usuario;
import com.isekaiyume.isekaiyume.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UsuarioController {
    @Autowired
    UsuarioRepositorio UsuarioRepositorio;

    //@GetMapping("/login")
  //  public String login(Model model){
    //    List<Usuario> usuarios = UsuarioRepositorio.findAll();
      //  model.addAttribute("usuarios",usuarios);
    //    return "li"

  //  }


   // @GetMapping("/registro")
  //  public String registro(Usuario usuario){
   //     UsuarioRepositorio.save(usuario);
     //   return "redirect/index";
  //  }
}
