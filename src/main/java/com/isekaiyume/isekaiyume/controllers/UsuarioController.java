package com.isekaiyume.isekaiyume.controllers;

import ch.qos.logback.classic.Logger;
import com.isekaiyume.isekaiyume.entidades.Usuario;
import com.isekaiyume.isekaiyume.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        // Agrega un objeto Usuario vacío al modelo para enlazar el formulario
        model.addAttribute("usuario", new Usuario());
        return "registro"; // Devuelve la vista del formulario de registro
    }

    @PostMapping("/registro")
    public String registro(@ModelAttribute Usuario usuario, Model model) {
        // Verificar si el usuario ya existe en la base de datos
        if (usuarioService.existeUsuario(usuario.getNombreUsuario())) {
            // El usuario ya existe, agrega un mensaje de error al modelo
            model.addAttribute("error", "El nombre de usuario ya está en uso. Por favor, elige otro nombre de usuario.");
            // Devuelve la vista de registro con el mensaje de error
            return "registro";
        }

        // Si el usuario no existe, realiza validaciones y procesamiento antes de guardar el usuario
        // Aquí puedes cifrar la contraseña antes de guardarla en la base de datos
        usuarioService.registrarNuevoUsuario(usuario);

        // Redirige a la página de inicio de sesión después del registro exitoso
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login"; // Devuelve la vista del formulario de inicio de sesión
    }
    Logger logger;
    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String nombreUsuario, @RequestParam String password) {
        // Autenticar al usuario utilizando el nombre de usuario y la contraseña
        Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario);

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            if (usuario.getRol().equals("ADMIN")) {

                return "redirect:/admin";
            } else {

                return "redirect:/";
            }
        } else {
            // Autenticación fallida, muestra un mensaje de error o redirige de nuevo al inicio de sesión
            return "redirect:/login?error";
        }
    }
    @GetMapping("/manejousuarios")
    public String manejousuarios(Model model) {
        // Recupera los usuarios de la base de datos utilizando el servicio de UsuarioService
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios(); // Debes implementar este método

        // Agrega la lista de usuarios al modelo para que esté disponible en la vista
        model.addAttribute("usuarios", usuarios);

        return "manejousuarios"; // Devuelve el nombre de la vista
    }

    @GetMapping("/modificarUsuario/{id}")
    public String mostrarFormularioModificacion(@PathVariable("id") Integer id, Model model) {
        // Recupera el usuario por su ID desde la base de datos
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

        // Agrega el usuario al modelo para que los datos se muestren en el formulario de modificación
        model.addAttribute("usuario", usuario);

        return "modificarUsuario"; // Devuelve el nombre de la vista del formulario de modificación
    }

    @PostMapping("/modificarUsuario/{id}")
    public String modificarUsuario(@PathVariable("id") Integer id, @ModelAttribute Usuario usuario, Model model) {
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(id);

        // Verificar si el usuario existe
        if (usuarioExistente != null) {
            // Verificar si el nuevo nombre de usuario ya está en uso por otro usuario
            if (!usuarioExistente.getNombreUsuario().equals(usuario.getNombreUsuario()) &&
                    usuarioService.existeUsuario(usuario.getNombreUsuario())) {
                model.addAttribute("error", "El nombre de usuario ya está en uso. Por favor, elige otro nombre de usuario.");
                return "modificarusuario";
            }

            // Actualizar los campos del usuario existente con los nuevos valores del formulario
            usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
            usuarioExistente.setRol(usuario.getRol());

            // Guardar el usuario modificado en la base de datos
            usuarioService.guardarUsuario(usuarioExistente);
        }
        return "redirect:/manejousuarios";
    }
    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id) {
        // Aquí debes implementar la lógica para eliminar el usuario por su ID
        usuarioService.eliminarUsuario(id);

        return "redirect:/manejousuarios";
    }

}
