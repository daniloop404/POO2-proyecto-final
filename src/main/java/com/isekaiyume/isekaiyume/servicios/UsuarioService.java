package com.isekaiyume.isekaiyume.servicios;

import com.isekaiyume.isekaiyume.entidades.Usuario;
import com.isekaiyume.isekaiyume.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarNuevoUsuario(Usuario usuario) {
        // Verificar si el usuario ya existe en la base de datos por su nombre de usuario
        if (existeUsuario(usuario.getNombreUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso. Por favor, elige otro nombre de usuario.");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("USER");
        usuarioRepositorio.save(usuario);
    }

    public boolean existeUsuario(String nombreUsuario) {
        // Verificar si un usuario con el mismo nombre de usuario ya existe en la base de datos
        return usuarioRepositorio.findByNombreUsuario(nombreUsuario) != null;
    }
    public Usuario findByNombreUsuario(String nombreUsuario) {
        // Buscar un usuario por su nombre de usuario en el repositorio
        return usuarioRepositorio.findByNombreUsuario(nombreUsuario);
    }

    // Otros métodos relacionados con la gestión de usuarios
}
    // Puedes agregar más métodos relacionados con la gestión de usuarios aquí, como buscar un usuario por nombre de usuario, etc.
