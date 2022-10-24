package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.RolDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.services.PermisoServiceImp;
import com.proyecto.is2.proyecto.services.ProyectoServiceImp;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/roles")
public class RolController {
    final String VIEW = "rol"; // identificador de la vista
    final String VIEW_PATH = "rol";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/roles";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/roles";
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

    @Autowired
    private ProyectoServiceImp proyectoServiceImp;

    @ModelAttribute("listPermisos")
    public List<Long> listaPermisos() {
        return new ArrayList<>();
    }

    @ModelAttribute("rol")
    public RolDTO instanciarObjetoDTO() {
        return new RolDTO();
    }

    @GetMapping
    public String mostrarCRUDTemplate(Model model) {

        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);

        if(consultar) {
            model.addAttribute("listRol", rolService.listar());
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
    public String formNuevo(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("rol") RolDTO objetoDTO,
            RedirectAttributes attributes) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Rol rol = new Rol();
            rolService.convertirDTO(rol, objetoDTO);
            rolService.guardar(rol);

            attributes.addFlashAttribute("message", "¡Rol creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}/delete")
    public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes) {
        this.operacion = "eliminar-";
        Long idRol;

        try {
            idRol = Long.parseLong(id);
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "¡Id rol no valido!");
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Rol rol = rolService.existeRol(idRol);
            rolService.eliminarRol(rol);
            attributes.addFlashAttribute("message", "Rol eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        Rol rol;

        // validar el id
        try {
            Long idRol = Long.parseLong(id);
            rol = rolService.existeRol(idRol);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        if(eliminar) {
            model.addAttribute("rol", rol);
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("rol") RolDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Rol rol;

        if (result.hasErrors()) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            rol = rolService.existeRol(id);
            if(rol != null) {
                rolService.convertirDTO(rol, objetoDTO);
                attributes.addFlashAttribute("message", "¡Rol actualizado correctamente!");
                rolService.guardar(rol);
                return RD_FORM_VIEW;
            } else {
                attributes.addFlashAttribute("message", "¡Id del rol no existe!");
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }



    @GetMapping("/{idRol}/permisos")
    public String verMiembrosProyecto(Model model, @PathVariable Long idRol) {
        if(usuarioService.tienePermiso("asignar-permisos-rol")) {
            Rol rol = rolService.existeRol(idRol);
            if(rol != null) {
                model.addAttribute("objRol", rol);
                model.addAttribute("listPermiso", permisoService.listar());
                model.addAttribute("listRolPermiso", rol.getPermisos());
                model.addAttribute("permisoAsignarPer", true);
                model.addAttribute("permisoEliminarPer", usuarioService.tienePermiso("eliminar-permisos-rol"));
                model.addAttribute("permisoVer", usuarioService.tienePermiso("consultar-permiso"));
                return "rol/asignar-permisos";
            }
        } else {
            return FALTA_PERMISO_VIEW;
        }
        System.out.println("ERROR. No se encontro rol con ID:" + idRol);
        return RD_FORM_VIEW;
    }

    /**
     * Agregar un nuevo permiso al rol.
     * @param idRol identificador del rol.
     * @param idPermiso identificador del permiso.
     * @return
     */
    @PostMapping("/agregar-permiso")
    public String agregarPermiso(@RequestParam("id_rol") Integer idRol, @RequestParam("id_permiso") Integer idPermiso) {
        this.operacion = "asignar-permisos-rol";

        if(usuarioService.tienePermiso(operacion)) {
            Rol rol = rolService.existeRol(idRol.longValue());
            Permiso permiso = permisoService.existePermiso(idPermiso.longValue());

            if(rol != null && permiso != null) {
                rol.getPermisos().add(permiso);
                rolService.guardar(rol);
            }

            return "redirect:/roles/" + idRol + "/permisos";
        }

        return RD_FALTA_PERMISO_VIEW;
    }

    /**
     * Eliminar un permiso del rol.
     * @param idRol identificador del rol.
     * @param idPermiso identificador del permiso.
     * @return
     */
    @PostMapping("/eliminar-permiso")
    public String eliminarPermiso(@RequestParam("id_rol") Long idRol, @RequestParam("id_permiso") Long idPermiso) {
        this.operacion = "asignar-permisos-rol";

        if(usuarioService.tienePermiso(operacion)) {
            Rol rol = rolService.existeRol(idRol);
            Permiso permiso = permisoService.existePermiso(idPermiso);

            if(rol != null && permiso != null) {
                rol.getPermisos().remove(permiso);
                rolService.guardar(rol);
            }

            return "redirect:/roles/" + idRol + "/permisos";
        }

        return RD_FALTA_PERMISO_VIEW;
    }

}
