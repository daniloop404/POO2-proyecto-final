package com.isekaiyume.isekaiyume.repositorios;

import com.isekaiyume.isekaiyume.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Usuario findByNombreUsuario(String nombreUsuario);
    
}
