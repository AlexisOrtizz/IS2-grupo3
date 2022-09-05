package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.ProyectoDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.services.ProyectoServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar CRUD en Proyecto
 */
@Controller
@RequestMapping("/proyecto")
public class ProyectoController implements CRUD<ProyectoDTO>{
    final String IDENTIFICADOR = "proyecto";
    String operacion = "";

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
    public String mostrarCRUDTemplate(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.existeUsuario(username);
        Set<Permiso> permisos = usuario.getRol().getPermisos();

        model.addAttribute("permisos", permisos);

        return "proyecto/formulario";
    }

    @Override
    public String mostrarObjetos() {
        this.operacion = "mostar-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            return "proyecto/lista";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    public String crearObjeto(@ModelAttribute("proyecto") ProyectoDTO objetoDTO) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Proyecto proyecto = proyectoService.convertirDTO(objetoDTO);
            proyectoService.guardar(proyecto);
            return "proyecto/fomularios";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    public String eliminarObjeto(@RequestParam("id") Long id) {
        this.operacion = "eliminar-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Proyecto proyecto = proyectoService.existeProyecto(id);
            proyectoService.eliminar(proyecto);
            return "proyecto/fomularios";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    public String actualizarObjeto(@ModelAttribute("proyecto") ProyectoDTO objetoDTO) {
        this.operacion = "actualizar-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Proyecto proyecto = proyectoService.existeProyecto(objetoDTO.getId());

            proyecto.setTitulo(objetoDTO.getTitulo());
            proyecto.setDescripcion(objetoDTO.getDescripcion());
            proyecto.setObservacion(objetoDTO.getObservacion());
            proyecto.setEstado(objetoDTO.getEstado());
            proyecto.setFechaInicio(objetoDTO.getFechaInicio());
            proyecto.setFechaFin(objetoDTO.getFechaFin());

            proyectoService.guardar(proyecto);
            return "proyecto/fomularios";
        } else {
            return "falta-permiso";
        }
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
    public String eliminarMiembro(Long idProyecto, String email) {
        this.operacion = "eliminar-miembro-proyecto";

        if(usuarioService.tienePermiso(operacion)) {
            Proyecto proyecto = proyectoService.existeProyecto(idProyecto);
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
