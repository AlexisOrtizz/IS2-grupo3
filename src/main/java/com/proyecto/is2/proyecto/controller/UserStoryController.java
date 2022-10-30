package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.SprintDTO;
import com.proyecto.is2.proyecto.controller.dto.StoryDTO;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.model.Sprint;
import com.proyecto.is2.proyecto.model.UserStory;
import com.proyecto.is2.proyecto.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proyectos/{idProject}/sprints/{idSprint}/historias")
public class UserStoryController {
    final String VIEW = GeneralUtils.STORY_CODE; // identificador de la vista
    final String VIEW_PATH = "us";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/historias";
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

    @Autowired
    private UserStoryServiceImp userStoryService;


    /**
     * Instancia un DTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Entity.
     * @return StoryDTO
     */
    @ModelAttribute("historia")
    public StoryDTO instanciarObjetoDTO() {
        return new StoryDTO();
    }

    @GetMapping
    public String mostrarCRUDTemplate(@PathVariable String idProject, @PathVariable String idSprint, Model model) {
        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);
        Proyecto proyecto = null;
        Sprint sprint = null;

        try {
            Long idP = Long.parseLong(idProject);
            proyecto = proyectoService.existeProyecto(idP);
            Long idS = Long.parseLong(idSprint);
            sprint = sprintService.existeObjeto(idS);

            if(sprint == null || proyecto == null) {
                model.addAttribute("error", "Error. Sprint: " + sprint + ". Proyecto: " + proyecto);
                return "general-error";
            }
        } catch(Exception e) {
            return "redirect:/proyectos/" + idProject + "/backlog";
        }

        if(consultar) {
            model.addAttribute("project", proyecto);
            model.addAttribute("objSprint", sprint);
        } else {
            return FALTA_PERMISO_VIEW;
        }

        model.addAttribute("permisoVer", consultar);
        model.addAttribute("permisoCrear", crear);
        model.addAttribute("permisoEliminar", eliminar);
        model.addAttribute("permisoActualizar", actualizar);

        return FORM_VIEW;
    }

    @GetMapping("/nuevo")
    public String formNuevo(@PathVariable Long idProject, @PathVariable Long idSprint, Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        if(crear) {
            Proyecto proyecto = proyectoService.existeProyecto(idProject);
            Sprint sprint = sprintService.existeObjeto(idSprint);

            if(proyecto != null && sprint != null) {
                model.addAttribute("project", proyecto);
                model.addAttribute("objSprint", sprint);
                model.addAttribute("prioridades", GeneralUtils.getStoryPrioridades());
                model.addAttribute("estados", GeneralUtils.getEstadosProyecto());
                model.addAttribute("empty", GeneralUtils.EMPTY_VALUE); // para valores nulos

                return this.FORM_NEW;
            } else {
                model.addAttribute("mensaje", "El id del proyecto: " + idProject + ", " +
                        "sprint: " + idSprint + "no es valido");
                return "general-error";
            }
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@PathVariable Long idProject, @PathVariable Long idSprint, @ModelAttribute("historia") StoryDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Sprint sprint = sprintService.existeObjeto(idSprint);
            UserStory userStory = new UserStory();

            userStoryService.convertirDTO(userStory, objetoDTO);
            userStory.setSprint(sprint); // relacion inversa
            sprint.getUserStories().add(userStory);

            userStoryService.guardar(userStory);
            sprintService.guardar(sprint);

            attributes.addFlashAttribute("message", "¡UserStory guardado correctamente!");

            return "redirect:/proyectos/" + idProject + "/sprints/" + idSprint + "/historias";
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String idProject, @PathVariable String idSprint, @PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        Proyecto proyecto;
        Sprint sprint;
        UserStory userStory;

        // validar el id
        try {
            Long idP = Long.parseLong(idProject);
            proyecto = proyectoService.existeProyecto(idP);
            Long idS = Long.parseLong(idSprint);
            sprint = sprintService.existeObjeto(idS);
            Long idUS = Long.parseLong(id);
            userStory = userStoryService.existeObjeto(idUS);
        } catch(Exception e) {
            return "redirect:/proyectos/" + idProject + "/sprints/" + idSprint + "/historias";
        }

        Long idUsuario = GeneralUtils.EMPTY_VALUE;
        if(userStory.getUsuario() != null) {
            idUsuario = userStory.getUsuario().getIdUsuario();
        }

        StoryDTO objeto = new StoryDTO(userStory.getIdHistoria(), userStory.getDescripcion(), userStory.getObservacion(),
                userStory.getEstado(), userStory.getPrioridad(), idUsuario);

        model.addAttribute("project", proyecto);
        model.addAttribute("objSprint", sprint);
        model.addAttribute("historiaEdit", objeto);
        model.addAttribute("prioridades", GeneralUtils.getStoryPrioridades());
        model.addAttribute("estados", GeneralUtils.getEstadosProyecto());
        model.addAttribute("empty", GeneralUtils.EMPTY_VALUE); // para valores nulos

        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long idProject, @PathVariable Long idSprint, @PathVariable Long id, @ModelAttribute("historia") StoryDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes, Model model) {
        this.operacion = "actualizar-";
        Proyecto proyecto;
        Sprint sprint;
        UserStory userStory;

        if (result.hasErrors()) {
            return "redirect:/proyectos/" + idProject + "/sprints/" + idSprint + "/historias";
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            proyecto = proyectoService.existeProyecto(idProject);
            sprint = sprintService.existeObjeto(idSprint);
            userStory = userStoryService.existeObjeto(id);
            if(proyecto != null && sprint != null && userStory != null) {
                sprint.getUserStories().remove(userStory); // sacar de la lista el anterior US
                userStoryService.convertirDTO(userStory, objetoDTO);
                sprint.getUserStories().add(userStory); // agregar a la lista el nuevo US

                userStoryService.guardar(userStory); // actualizar US
                sprintService.guardar(sprint);

                attributes.addFlashAttribute("message", "¡UserStory actualizado correctamente!");

                return "redirect:/proyectos/" + idProject + "/sprints/" + idSprint + "/historias";
            } else {
                model.addAttribute("error", "Error. Sprint: " + sprint + ". Proyecto: " + proyecto + ". UserStory: " + userStory);
                return "general-error";
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
    public String eliminarObjeto(@PathVariable String idProject, @PathVariable String idSprint, @PathVariable String id, RedirectAttributes attributes) {
        this.operacion = "eliminar-";
        Long idS;
        Long idUS;

        try {
            idS = Long.parseLong(idSprint);
            idUS = Long.parseLong(id);
        } catch (Exception e) {
            return "redirect:/proyectos/" + idProject + "/sprints/" + idSprint + "/historias";
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Sprint sprint = sprintService.existeObjeto(idS);
            UserStory userStory = userStoryService.existeObjeto(idUS);

            // agregar el Sprint en estado eliminar y sacar la referencia del proyecto.
            userStory.setEstado(GeneralUtils.getEstadoEliminado());
            sprint.getUserStories().remove(userStory);
            userStory.setSprint(null);

            userStoryService.guardar(userStory);
            sprintService.guardar(sprint);

            attributes.addFlashAttribute("message", "¡UserStory eliminado correctamente!");

            return "redirect:/proyectos/" + idProject + "/sprints/" + idSprint + "/historias";
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }
}
