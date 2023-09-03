package com.isekaiyume.isekaiyume.entidades;

import lombok.Data;

import jakarta.persistence.*;
@Data
@Entity
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombreEpisodio;
    private String videoUrl; // URL for the episode video

    @ManyToOne
    private Titulo titulo; // Many episodes belong to one title (anime)

    // Add other fields and getters/setters
}





