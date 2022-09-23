package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.RolDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.services.PermisoServiceImp;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("rol")
public class RolController implements CRUD<RolDTO> {
    final String VIEW = "rol";
    String operacion = "";
    final String FORM_VIEW = VIEW + "/form";
    final String RD_FORM_VIEW = "redirect:/" + FORM_VIEW;
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String LISTA_VIEW = VIEW + "/listar";
    final String ASIGNAR_ROL_VIEW = VIEW + "/asignar-rol";
    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;

    @Autowired
    private RolServiceImp rolService;

    @Autowired
    private PermisoServiceImp permisoService;

    @Autowired
    private UsuarioServiceImp usuarioService;

    @ModelAttribute("listPermisos")
    public List<Long> listaPermisos() {
        return new ArrayList<>();
    }

    @ModelAttribute("rol")
    public RolDTO instanciarObjetoDTO() {
        return new RolDTO();
    }

    @Override
    @GetMapping("form")
    public String mostrarCRUDTemplate(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);

        if(actualizar || eliminar) {
            model.addAttribute("idRoles", rolService.listar());
        } else {
            model.addAttribute("idRoles", null);
        }

        model.addAttribute("crear", crear);
        model.addAttribute("eliminar", eliminar);
        model.addAttribute("actualizar", actualizar);

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
    public String crearObjeto(@ModelAttribute("rol") RolDTO objetoDTO) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Rol rol = new Rol();
            rolService.convertirDTO(rol, objetoDTO);
            rolService.guardar(rol);
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @Override
    @PostMapping("eliminar")
    public String eliminarObjeto(@RequestParam("id_rol") Integer id) {
        this.operacion = "eliminar-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Rol rol = rolService.existeRol(id.longValue());
            rolService.eliminarRol(rol);
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @Override
    @PostMapping("actualizar")
    public String actualizarObjeto(@ModelAttribute("rol") RolDTO objetoDTO) {
        this.operacion = "actualizar-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Rol rol = rolService.existeRol(objetoDTO.getIdRol().longValue());
            if(rol != null) {
                rolService.convertirDTO(rol, objetoDTO);
                rolService.guardar(rol);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("permisos")
    public String verMiembrosProyecto(Model model) {
        if(usuarioService.tienePermiso("asignar-permisos-rol")) {
            model.addAttribute("roles", rolService.listar());
            model.addAttribute("permisos", permisoService.listar());
            return "rol/permisos";
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    /**
     * Agregar un nuevo permiso al rol.
     * @param idRol identificador del rol.
     * @param idPermiso identificador del permiso.
     * @return
     */
    @PostMapping("agregar-permiso")
    public String agregarPermiso(@RequestParam("id_rol") Integer idRol, @RequestParam("id_permiso") Integer idPermiso) {
        this.operacion = "asignar-permisos-rol";

        if(usuarioService.tienePermiso(operacion)) {
            Rol rol = rolService.existeRol(idRol.longValue());
            Permiso permiso = permisoService.existePermiso(idPermiso.longValue());

            if(rol != null && permiso != null) {
                rol.getPermisos().add(permiso);
                rolService.guardar(rol);
            }

            return "redirect:/rol/permisos";
        }

        return RD_FALTA_PERMISO_VIEW;
    }

    /**
     * Eliminar un permiso del rol.
     * @param idRol identificador del rol.
     * @param idPermiso identificador del permiso.
     * @return
     */
    @PostMapping("eliminar-permiso")
    public String eliminarPermiso(@RequestParam("id_rol") Integer idRol, @RequestParam("id_permiso") Integer idPermiso) {
        this.operacion = "asignar-permisos-rol";

        if(usuarioService.tienePermiso(operacion)) {
            Rol rol = rolService.existeRol(idRol.longValue());
            Permiso permiso = permisoService.existePermiso(idPermiso.longValue());

            if(rol != null && permiso != null) {
                rol.getPermisos().remove(permiso);
                rolService.guardar(rol);
            }

            return "redirect:/rol/permisos";
        }

        return RD_FALTA_PERMISO_VIEW;
    }

}
