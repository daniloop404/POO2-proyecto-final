package com.isekaiyume.isekaiyume.controllers;

import com.isekaiyume.isekaiyume.entidades.Usuario;
import com.isekaiyume.isekaiyume.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String nombreUsuario, @RequestParam String password) {
        // Autenticar al usuario utilizando el nombre de usuario y la contraseña
        Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario);

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword()))
        {
            // Usuario autenticado, puedes redirigirlo a su perfil o página de inicio
            return "redirect:/"; // Cambia "/inicio" a la página de inicio real de tu aplicación
        } else {
            // Autenticación fallida, muestra un mensaje de error o redirige de nuevo al inicio de sesión
            return "redirect:/login?error";
            // Cambia "/login" según corresponda
        }
    }

}
