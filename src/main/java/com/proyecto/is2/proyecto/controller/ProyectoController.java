package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.ProyectoDTO;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.model.*;
import com.proyecto.is2.proyecto.services.ProyectoServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar CRUD en Proyecto
 */
@Controller
@RequestMapping("proyectos")
public class ProyectoController {
    final String VIEW = "proyecto"; // identificador de la vista
    final String VIEW_PATH = "proyecto";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/proyectos";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/proyectos";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String BACKLOG_VIEW = "backlog/backlog";
    final String BURNDOWN_CHART_VIEW = "reporte/burndown-chart";
    final List<String> ESTADOS = Arrays.asList("Pendiente","Activo", "Cancelado", "Finalizado");

    @Autowired
    private UsuarioServiceImp usuarioService;

    @Autowired
    private ProyectoServiceImp proyectoService; // llamada a los servicios de proyecto


    /**
     * Instancia un ProyectoDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Proyecto.
     * @return ProyectoDTO
     */
    @ModelAttribute("proyecto")
    public ProyectoDTO instanciarObjetoDTO() {
        return new ProyectoDTO();
    }


    @GetMapping
    public String mostrarCRUDTemplate(Model model) {

        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);

        if(consultar) {
            model.addAttribute("listProject", proyectoService.listarProyectos());
        } else {
            return FALTA_PERMISO_VIEW;
        }

        model.addAttribute("permisoVer", consultar);
        model.addAttribute("permisoCrear", crear);
        model.addAttribute("permisoEliminar", eliminar);
        model.addAttribute("permisoActualizar", actualizar);

        model.addAttribute("estados", this.ESTADOS);

        return FORM_VIEW;
    }

    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        if(crear) {
            model.addAttribute("estados", this.ESTADOS);
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("proyecto") ProyectoDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Proyecto proyecto = new Proyecto();
            proyectoService.convertirDTO(proyecto, objetoDTO);
            proyectoService.guardar(proyecto);
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        Proyecto proyecto;

        // validar el id
        try {
            Long idProyecto = Long.parseLong(id);
            proyecto = proyectoService.existeProyecto(idProyecto);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("project", proyecto);
        model.addAttribute("estados", this.ESTADOS);

        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("proyecto") ProyectoDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Proyecto proyecto;

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            proyecto = proyectoService.existeProyecto(id);
            if(proyecto != null) {
                proyectoService.convertirDTO(proyecto, objetoDTO);

                attributes.addFlashAttribute("message", "¡Proyecto actualizado correctamente!");
                proyectoService.guardar(proyecto);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
    public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes) {
        this.operacion = "eliminar-";
        Long idProyecto;

        try {
            idProyecto = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Proyecto proyecto = proyectoService.existeProyecto(idProyecto);
            proyectoService.eliminar(proyecto);
            attributes.addFlashAttribute("message", "¡Proyecto eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

//    @GetMapping("miembros")
//    public String verMiembrosProyecto(Model model) {
//        if(usuarioService.tienePermiso("agregar-miembro-proyecto")) {
//            model.addAttribute("proyectos", proyectoService.listarProyectos());
//            model.addAttribute("usuarios", usuarioService.listarUsuarios());
//            return "proyecto/miembros";
//        } else {
//            return FALTA_PERMISO_VIEW;
//        }
//    }

//    /**
//     * Agregar un nuevo miembro al equipo del Proyecto.
//     * @param idProyecto identificador del proyecto.
//     * @param email identificador del miembro a agregar.
//     * @return
//     */
//    @PostMapping("agregar-miembro")
//    public String agregarMiembro(@RequestParam("id_proyecto") Integer idProyecto, @RequestParam("email") String email) {
//        this.operacion = "agregar-miembro-proyecto";
//
//        if(usuarioService.tienePermiso(operacion)) {
//            Proyecto proyecto = proyectoService.existeProyecto(idProyecto.longValue());
//            Usuario usuario = usuarioService.existeUsuario(email);
//
//            if(usuario != null && proyecto != null) {
//                proyecto.getEquipo().add(usuario);
//                proyectoService.guardar(proyecto);
//            }
//
//            return "redirect:/proyecto/miembros";
//        }
//
//        return RD_FALTA_PERMISO_VIEW;
//    }

//    /**
//     * Eliminar un miembro del equipo del Proyecto.
//     * @param idProyecto identificador del proyecto.
//     * @param email identificador del miembro a eliminar.
//     * @return
//     */
//    @PostMapping("eliminar-miembro")
//    public String eliminarMiembro(@RequestParam("id_proyecto") Integer idProyecto, String email) {
//        this.operacion = "eliminar-miembro-proyecto";
//
//        if(usuarioService.tienePermiso(operacion)) {
//            Proyecto proyecto = proyectoService.existeProyecto(idProyecto.longValue());
//            Usuario usuario = usuarioService.existeUsuario(email);
//
//            if(usuario != null && proyecto != null) {
//                if(proyecto.getEquipo().remove(usuario)) {
//                    proyectoService.guardar(proyecto);
//                    System.out.println("SE HA REMOVIDO CORRECTAMENTE EL MIEMBRO DEL PROYECTO");
//                } else {
//                    System.out.println("HA OCURRIDO UN ERROR AL QUERER QUITAR EL USUARIO DEL PROYECTO");
//                }
//            }
//
//            return "redirect:/proyecto/miembros";
//        }
//
//        return RD_FALTA_PERMISO_VIEW;
//    }

    @GetMapping("/{id}/backlog")
    public String verBacklog(@PathVariable String id, Model model) {
        try{
            Long idProject = Long.parseLong(id);
            Proyecto proyecto = proyectoService.existeProyecto(idProject);

            if(proyecto != null) {
                model.addAttribute("project", proyecto);
                return this.BACKLOG_VIEW;
            } else {
                model.addAttribute("mensaje", "El id del proyecto no es valido");
                return "general-error";
            }
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/{id}/reporte")
    public String verReporte(@PathVariable String id, Model model) {
        return this.BURNDOWN_CHART_VIEW;
    }

}
