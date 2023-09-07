package com.isekaiyume.isekaiyume.controllers;

import com.isekaiyume.isekaiyume.entidades.Episodio;
import com.isekaiyume.isekaiyume.entidades.Titulo;
import com.isekaiyume.isekaiyume.repositorios.EpisodioRepositorio;
import com.isekaiyume.isekaiyume.repositorios.TituloRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/manejocatalogo")
    public String mostrarCatalogo(Model model) {
        List<Titulo> titulos = tituloRepositorio.findAll();
        model.addAttribute("titulos", titulos);
        return "manejocatalogo";
    }

    @GetMapping("/agregaranime")
    public String mostrarFormulario(Model model) {
        model.addAttribute("titulo", new Titulo());
        model.addAttribute("episodio", new Episodio());
        return "/agregaranime";
    }

    @PostMapping("/crearTituloAnime")
    public String crearTituloAnime(@ModelAttribute("titulo") Titulo titulo,
                                   @RequestParam("nombreEpisodios") List<String> nombresEpisodio,
                                   @RequestParam("videoUrls") List<String> videoUrls) {

        // Guardar el título del anime
        tituloRepositorio.save(titulo);

        // Guardar los episodios
        List<Episodio> episodios = new ArrayList<>();

        for (int i = 0; i < nombresEpisodio.size(); i++) {
            String nombreEpisodio = nombresEpisodio.get(i);
            String videoUrl = videoUrls.get(i);

            Episodio episodio = new Episodio();
            episodio.setNombreEpisodio(nombreEpisodio);
            episodio.setVideoUrl(videoUrl);
            episodio.setTitulo(titulo);

            episodios.add(episodio);
        }

        episodioRepositorio.saveAll(episodios);

        return "redirect:/catalogo";
    }

    @GetMapping("/modificarAnime/{tituloId}")
    public String editarAnime(@PathVariable("tituloId") Integer tituloId, Model model) {
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(tituloId);

        if (tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();

            // Puedes agregar el título a tu modelo para que esté disponible en la vista de edición
            model.addAttribute("titulo", titulo);

            // Puedes cargar otros datos o realizar otras operaciones necesarias antes de mostrar la vista de edición
            // ...

            return "modificaranime"; // Cambia esto al nombre de tu vista de edición
        } else {
            // Manejo de errores o redirección si el título no se encuentra
            return "error"; // Cambia esto al nombre de tu página de error
        }
    }



    @PostMapping("/modificarAnime")
    public String modificarTituloAnime(@ModelAttribute("anime") Titulo anime) {
        // Recupera el anime original de la base de datos usando su ID
        Titulo animeOriginal = tituloRepositorio.findById(anime.getId()).orElse(null);

        if (animeOriginal != null) {
            // Actualiza los campos del anime con los valores del formulario
            animeOriginal.setNombreTitulo(anime.getNombreTitulo());
            animeOriginal.setDescripcion(anime.getDescripcion());
            animeOriginal.setImagenUrl(anime.getImagenUrl());

            // Actualiza los episodios
            List<Episodio> episodiosOriginales = animeOriginal.getEpisodios();
            List<Episodio> episodiosNuevos = anime.getEpisodios();

            // Borra los episodios que ya no están en la lista de episodios nuevos
            episodiosOriginales.removeIf(episodio -> !episodiosNuevos.contains(episodio));

            // Actualiza los campos de los episodios existentes
            for (int i = 0; i < episodiosOriginales.size(); i++) {
                Episodio episodioOriginal = episodiosOriginales.get(i);
                Episodio episodioNuevo = episodiosNuevos.get(i);

                episodioOriginal.setNombreEpisodio(episodioNuevo.getNombreEpisodio());
                episodioOriginal.setVideoUrl(episodioNuevo.getVideoUrl());
            }

            // Agrega los nuevos episodios
            episodiosNuevos.removeAll(episodiosOriginales);
            episodiosOriginales.addAll(episodiosNuevos);

            // Actualiza el anime con los episodios modificados
            animeOriginal.setEpisodios(episodiosOriginales);

            // Guarda los cambios en la base de datos
            tituloRepositorio.save(animeOriginal);
        }

        return "redirect:/catalogo";
    }
    @PostMapping("/eliminarAnime/{tituloId}")
    public String eliminarAnime(@PathVariable("tituloId") Integer tituloId) {
        // Obtener el título que se va a eliminar
        Optional<Titulo> tituloOptional = tituloRepositorio.findById(tituloId);

        if (tituloOptional.isPresent()) {
            Titulo titulo = tituloOptional.get();

            // Eliminar todos los episodios relacionados con este título
            episodioRepositorio.deleteAll(titulo.getEpisodios());

            // Finalmente, eliminar el título
            tituloRepositorio.delete(titulo);

            // Redirige a la página del catálogo u otra ubicación
            return "redirect:/catalogo";
        } else {
            // Manejo de errores si el título no se encuentra
            return "error";
        }
    }
}


