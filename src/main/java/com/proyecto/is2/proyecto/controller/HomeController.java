package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UsuarioServiceImp usuarioService;

    @GetMapping("/")
    public String home() {
        // retorna el nombre de la vista
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        /* crear admin si no existe */
        if(usuarioService.existeUsuario("admin@gmail.com") == null) {
            usuarioService.crearAdmin();
        }

        // retorna el nombre de la vista
        return "login";
    }
}
