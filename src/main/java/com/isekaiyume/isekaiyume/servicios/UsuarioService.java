package com.isekaiyume.isekaiyume.servicios;

import com.isekaiyume.isekaiyume.entidades.Usuario;
import com.isekaiyume.isekaiyume.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepositorio.findAll();
    }
    public Usuario obtenerUsuarioPorId(Integer id) {
        // Recupera un usuario por su ID desde el repositorio
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);

        // Si el usuario existe, devuelve el objeto Usuario, de lo contrario, devuelve null
        return usuario.orElse(null);
    }
    public String guardarUsuario(Usuario usuario) {
        // Verificar si el nombre de usuario ya está en uso por otro usuario
        if (existeUsuarioConMismoNombre(usuario.getNombreUsuario(), usuario.getId())) {
            return "El nombre de usuario ya está en uso. Por favor, elige otro nombre de usuario.";
        }

        // Guardar el usuario en la base de datos
        usuarioRepositorio.save(usuario);

        // Devolver un mensaje de éxito
        return "Usuario guardado exitosamente.";
    }

    private boolean existeUsuarioConMismoNombre(String nombreUsuario, Integer id) {
        // Verificar si un usuario con el mismo nombre de usuario ya existe en la base de datos
        Usuario usuarioExistente = usuarioRepositorio.findByNombreUsuario(nombreUsuario);

        // Si el usuario existente tiene un ID diferente al usuario que estamos modificando, significa que
        // el nombre de usuario ya está en uso por otro usuario
        return usuarioExistente != null && !usuarioExistente.getId().equals(id);
    }
    public void eliminarUsuario(Integer id) {
        // Busca el usuario por su ID
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);

        if (usuario != null) {
            // Elimina el usuario de la base de datos
            usuarioRepositorio.delete(usuario);
        }
    }
    // Otros métodos relacionados con la gestión de usuarios
}
    // Puedes agregar más métodos relacionados con la gestión de usuarios aquí, como buscar un usuario por nombre de usuario, etc.
