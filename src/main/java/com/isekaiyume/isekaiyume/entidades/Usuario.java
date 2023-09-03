package com.isekaiyume.isekaiyume.entidades;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombreUsuario;
    private String password;
    // Add other fields and getters/setters
}
