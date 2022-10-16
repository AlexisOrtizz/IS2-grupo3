package com.proyecto.is2.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("proyectos/{idProject}/sprint")
public class SprintController {

    @GetMapping("/{idSprint}")
    public String mostrarCRUDTemplate(@PathVariable String idProject, @PathVariable String idSprint, Model model) {
        return "sprint/sprints";
    }
}
