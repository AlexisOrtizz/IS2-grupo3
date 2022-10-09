package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    public final static String HOME_VIEW = "index";
    public final static String LOGIN_VIEW = "login";

    @Autowired
    private UsuarioServiceImp usuarioService;

    @GetMapping("/")
    public String home() {
        // retorna el nombre de la vista
        return HOME_VIEW;
    }

    @GetMapping("/login")
    public String login() {
        /* crear admin si no existe */
        if(usuarioService.existeUsuario(GeneralUtils.ADMIN_EMAIL) == null) {
            usuarioService.crearAdmin();
        }

        // retorna el nombre de la vista
        return LOGIN_VIEW;
    }
}
