package com.isekaiyume.isekaiyume.controllers;

import com.isekaiyume.isekaiyume.entidades.Episodio;
import com.isekaiyume.isekaiyume.entidades.Titulo;
import com.isekaiyume.isekaiyume.repositorios.EpisodioRepositorio;
import com.isekaiyume.isekaiyume.repositorios.TituloRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TituloController {
    @Autowired
    TituloRepositorio tituloRepositorio;
    @Autowired
    EpisodioRepositorio episodioRepositorio;
    @GetMapping("/catalogo")
    public String catalogo(Model model){

        List<Titulo> titulos = tituloRepositorio.findAll();
        model.addAttribute("titulos",titulos);
        return "catalogo";
    }
    @GetMapping("/listaepisodios/{nombreAnime}")
    public String episodios(@PathVariable String nombreAnime, Model model) {
        // Lógica para obtener el título por nombre en lugar de ID
        Titulo titulo = tituloRepositorio.findByNombreTitulo(nombreAnime);
        if (titulo != null) {
            List<Episodio> episodios = episodioRepositorio.findByTitulo(titulo);
            model.addAttribute("titulo", titulo);
            model.addAttribute("episodios", episodios);
            return "listaepisodios";
        }
        // Manejo de errores si el título no se encuentra
        return "error"; // Crea una página de error personalizada si es necesario
    }

}
