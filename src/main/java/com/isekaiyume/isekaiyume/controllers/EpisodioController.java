package com.isekaiyume.isekaiyume.controllers;
import com.isekaiyume.isekaiyume.entidades.Episodio;
import com.isekaiyume.isekaiyume.repositorios.EpisodioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class EpisodioController {

    @Autowired
    EpisodioRepositorio episodioRepositorio;

    @GetMapping("/episodio/{id}")
    public String episodio(@PathVariable Integer id, Model model) {
        Episodio episodio = episodioRepositorio.findById(id).orElse(null);
        if (episodio != null) {
            model.addAttribute("titulo", episodio.getTitulo().getNombreTitulo());
            model.addAttribute("tituloEpisodio", episodio.getNombreEpisodio());
            model.addAttribute("url", episodio.getVideoUrl());
            return "episodio";
        }
        // Manejo de errores si el episodio no se encuentra
        return "error"; // Crea una página de error personalizada si es necesario
    }
}
