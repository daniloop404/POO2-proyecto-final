package com.isekaiyume.isekaiyume.repositorios;

import com.isekaiyume.isekaiyume.entidades.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TituloRepositorio extends JpaRepository<Titulo,Integer> {
    Titulo findByNombreTitulo(String nombreTitulo);
}
