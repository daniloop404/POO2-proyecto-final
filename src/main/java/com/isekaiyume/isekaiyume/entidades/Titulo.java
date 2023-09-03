package com.isekaiyume.isekaiyume.entidades;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;
@Data
@Entity
public class Titulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombreTitulo;
    private String descripcion;
    private String imagenUrl;

    @OneToMany(mappedBy = "titulo")
    private List<Episodio> episodios;
    // Add other fields and getters/setters
}
