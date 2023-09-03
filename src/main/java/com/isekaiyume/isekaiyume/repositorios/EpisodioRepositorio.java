package com.isekaiyume.isekaiyume.repositorios;

import com.isekaiyume.isekaiyume.entidades.Episodio;
import com.isekaiyume.isekaiyume.entidades.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EpisodioRepositorio extends JpaRepository<Episodio,Integer> {
    @Query("SELECT e FROM Episodio e WHERE e.titulo = :titulo")
    List<Episodio> findByTitulo(@Param("titulo") Titulo titulo);
}
