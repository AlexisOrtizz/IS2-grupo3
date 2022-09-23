package com.proyecto.is2.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoxController {

    /* retorna un fragmento */
    @GetMapping("/box")
    public String getBox() {
        return "fragments/box :: post";
    }

}
