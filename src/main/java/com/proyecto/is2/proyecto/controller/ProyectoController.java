package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.ProyectoDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.services.ProyectoServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.Set;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar CRUD en Proyecto
 */
@Controller
@RequestMapping("proyecto")
public class ProyectoController implements CRUD<ProyectoDTO>{
    final String VIEW = "proyecto";
    String operacion = "";
    final String FORM_VIEW = VIEW + "/form";
    final String RD_FORM_VIEW = "redirect:/" + FORM_VIEW;
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String LISTA_VIEW = VIEW + "/listar";

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

    @Override
    @GetMapping("form")
    public String mostrarCRUDTemplate(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);

        if(actualizar || eliminar) {
            model.addAttribute("idProyectos", proyectoService.listarProyectos());
        } else {
            model.addAttribute("idProyectos", null);
        }

        model.addAttribute("crear", crear);
        model.addAttribute("eliminar", eliminar);
        model.addAttribute("actualizar", actualizar);

        model.addAttribute("estados", Arrays.asList("Pendiente","Activo", "Cancelado", "Finalizado"));

        return FORM_VIEW;
    }

    @Override
    @GetMapping("lista")
    public String mostrarObjetos() {
        this.operacion = "mostrar-";

        if(usuarioService.tienePermiso(operacion + VIEW)){
            return LISTA_VIEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @Override
    @PostMapping("crear")
    public String crearObjeto(@ModelAttribute("proyecto") ProyectoDTO objetoDTO) {
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

    @Override
    @PostMapping("eliminar")
    public String eliminarObjeto(@RequestParam("id_proyecto") Integer id) {
        this.operacion = "eliminar-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Proyecto proyecto = proyectoService.existeProyecto(id.longValue());
            proyectoService.eliminar(proyecto);
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @Override
    @PostMapping("actualizar")
    public String actualizarObjeto(@ModelAttribute("proyecto") ProyectoDTO objetoDTO) {
        this.operacion = "actualizar-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Proyecto proyecto = proyectoService.existeProyecto(objetoDTO.getIdProyecto().longValue());
            if(proyecto != null) {
                proyectoService.convertirDTO(proyecto, objetoDTO);
                proyectoService.guardar(proyecto);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    /**
     * Agregar un nuevo miembro al equipo del Proyecto.
     * @param idProyecto identificador del proyecto.
     * @param email identificador del miembro a agregar.
     * @return
     */
    @PostMapping("agregar-miembro")
    public String agregarMiembro(@RequestParam("id_proyecto") Long idProyecto, @RequestParam("username") String email) {
        this.operacion = "agregar-miembro-proyecto";

        if(usuarioService.tienePermiso(operacion)) {
            Proyecto proyecto = proyectoService.existeProyecto(idProyecto);
            Usuario usuario = usuarioService.existeUsuario(email);

            if(usuario != null && proyecto != null) {
                proyecto.getEquipo().add(usuario);
            }

            return "miembros-proyecto";
        }

        return "falta-permiso";
    }

    /**
     * Eliminar un miembro del equipo del Proyecto.
     * @param idProyecto identificador del proyecto.
     * @param email identificador del miembro a eliminar.
     * @return
     */
    @PostMapping("eliminar-miembro")
    public String eliminarMiembro(@RequestParam("id_proyecto") Integer idProyecto, String email) {
        this.operacion = "eliminar-miembro-proyecto";

        if(usuarioService.tienePermiso(operacion)) {
            Proyecto proyecto = proyectoService.existeProyecto(idProyecto.longValue());
            Usuario usuario = usuarioService.existeUsuario(email);

            if(usuario != null && proyecto != null) {
                if(proyecto.getEquipo().remove(usuario)) {
                    System.out.println("SE HA REMOVIDO CORRECTAMENTE EL MIEMBRO DEL PROYECTO");
                } else {
                    System.out.println("HA OCURRIDO UN ERROR AL QUERER QUITAR EL USUARIO DEL PROYECTO");
                }
            }

            return "miembros-proyecto";
        }

        return "falta-permiso";
    }

}
