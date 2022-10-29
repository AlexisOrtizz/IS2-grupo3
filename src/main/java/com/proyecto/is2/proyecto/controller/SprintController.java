package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.SprintDTO;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.model.Sprint;
import com.proyecto.is2.proyecto.services.BacklogServiceImp;
import com.proyecto.is2.proyecto.services.ProyectoServiceImp;
import com.proyecto.is2.proyecto.services.SprintServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proyectos/{idProject}/sprints")
public class SprintController {
    final String VIEW = "sprint"; // identificador de la vista
    final String VIEW_PATH = "sprint";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/sprints";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;

    @Autowired
    private UsuarioServiceImp usuarioService;

    @Autowired
    private SprintServiceImp sprintService;

    @Autowired
    private ProyectoServiceImp proyectoService;

    @Autowired
    private BacklogServiceImp backlogService;


    /**
     * Instancia un DTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Entity.
     * @return SprintDTO
     */
    @ModelAttribute("sprint")
    public SprintDTO instanciarObjetoDTO() {
        return new SprintDTO();
    }

    @GetMapping("/nuevo")
    public String formNuevo(@PathVariable Long idProject, Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        if(crear) {
            Proyecto proyecto = proyectoService.existeProyecto(idProject);

            if(proyecto != null) {
                model.addAttribute("project", proyecto);
                model.addAttribute("estados", GeneralUtils.getEstadosSprint());
                return this.FORM_NEW;
            } else {
                model.addAttribute("mensaje", "El id del proyecto no es valido");
                return "general-error";
            }
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@PathVariable Long idProject, @ModelAttribute("sprint") SprintDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Proyecto proyecto = proyectoService.existeProyecto(idProject);
            Sprint sprint = new Sprint();

            sprintService.convertirDTO(sprint, objetoDTO);
            sprint.setBacklog(proyecto.getBacklog()); // relacion inversa
            proyecto.getBacklog().getSprints().add(sprint);
            sprintService.guardar(sprint);
            backlogService.guardar(proyecto.getBacklog());
            proyectoService.guardar(proyecto);

            attributes.addFlashAttribute("message", "¡Sprint guardado correctamente!");

            return "redirect:/proyectos/" + proyecto.getIdProyecto() + "/backlog";
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String idProject, @PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        Proyecto proyecto;
        Sprint sprint;

        // validar el id
        try {
            Long idP = Long.parseLong(idProject);
            proyecto = proyectoService.existeProyecto(idP);
            Long idS = Long.parseLong(id);
            sprint = sprintService.existeObjeto(idS);
        } catch(Exception e) {
            return "redirect:/proyectos/" + idProject + "/backlog";
        }

        SprintDTO objeto = new SprintDTO(sprint.getIdSprint(), sprint.getTitulo(), sprint.getDuracion(),
                GeneralUtils.getStringToDate(sprint.getFechaInicio()), GeneralUtils.getStringToDate(sprint.getFechaFin()),
                sprint.getEstado());

        model.addAttribute("project", proyecto);
        model.addAttribute("sprintEdit", objeto);
        model.addAttribute("estados", GeneralUtils.getEstadosSprint());

        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long idProject, @PathVariable Long id, @ModelAttribute("sprint") SprintDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Proyecto proyecto;
        Sprint sprint;

        if (result.hasErrors()) {
            return "redirect://proyectos/" + idProject + "/backlog";
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            proyecto = proyectoService.existeProyecto(idProject);
            sprint = sprintService.existeObjeto(id);
            if(proyecto != null || sprint != null) {
                proyecto.getBacklog().getSprints().remove(sprint); // sacar de la lista el anterior sprint
                sprintService.convertirDTO(sprint, objetoDTO);
                proyecto.getBacklog().getSprints().add(sprint); // agregar a la lista el nuevo sprint
                sprintService.guardar(sprint); // actualizar sprint
                backlogService.guardar(proyecto.getBacklog());
                proyectoService.guardar(proyecto);

                attributes.addFlashAttribute("message", "¡Sprint actualizado correctamente!");

                return "redirect:/proyectos/" + proyecto.getIdProyecto() + "/backlog";
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
    public String eliminarObjeto(@PathVariable String idProject, @PathVariable String id, RedirectAttributes attributes) {
        this.operacion = "eliminar-";
        Long idP;
        Long idS;

        try {
            idP = Long.parseLong(idProject);
            idS = Long.parseLong(id);
        } catch (Exception e) {
            return "redirect:/proyectos/" + idProject + "/backlog";
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Proyecto proyecto = proyectoService.existeProyecto(idP);
            Sprint sprint = sprintService.existeObjeto(idS);

            // agregar el Sprint en estado eliminar y sacar la referencia del proyecto.
            sprint.setEstado(GeneralUtils.getEstadoEliminado());
            proyecto.getBacklog().getSprints().remove(sprint);
            sprint.setBacklog(null);
            sprintService.guardar(sprint);
            backlogService.guardar(proyecto.getBacklog());
            proyectoService.guardar(proyecto);

            attributes.addFlashAttribute("message", "¡Sprint eliminado correctamente!");

            return "redirect:/proyectos/" + idProject + "/backlog";
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }
}
