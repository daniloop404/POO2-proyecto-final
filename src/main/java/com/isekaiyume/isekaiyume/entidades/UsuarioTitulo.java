package com.isekaiyume.isekaiyume.entidades;
import lombok.Data;

import jakarta.persistence.*;
@Data
@Entity
public class UsuarioTitulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Titulo titulo;
}
