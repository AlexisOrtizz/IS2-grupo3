package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar CRUD sobre Usuario
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    final String VIEW = "usuario"; // identificador de la vista
    final String VIEW_PATH = "usuario";
    String operacion = "";
    final String FORM_VIEW = VIEW_PATH + "/usuarios";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/usuarios";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String ASIGNAR_ROL_VIEW = VIEW_PATH + "/asignar-rol";
//    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
    final String P_ASIGNAR_ROL = "asignar-rol-usuario";

    @Autowired
    private UsuarioServiceImp usuarioService; // llamada a los servicios de usuario

    @Autowired
    private RolServiceImp rolService;

    /**
     * Instancia un UsuarioDTO para rellenar con datos
     * del formulario para luego mapearlo a un objeto
     * Usuario.
     * @return
     */
    @ModelAttribute("usuario")
    public UsuarioDTO instanciarObjetoDTO() {
        return new UsuarioDTO();
    }

    @GetMapping
    public String mostrarGrilla(Model model) {

        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);

        //        if(!crear && !eliminar && !actualizar) {
        //            return FALTA_PERMISO_VIEW;
        //        }

        if(consultar) {
            model.addAttribute("listUser", usuarioService.listarUsuarios());
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
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);

        if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);

        if(crear) {
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("usuario") UsuarioDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

//        if (result.hasErrors()) {
//            return FORM_NEW;
//        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Usuario usuario = new Usuario();
            usuarioService.convertirDTO(usuario, objetoDTO);

            // si tiene permiso se le asigna el rol con id del formulario
            // sino se le asignar un rol por defecto.
            if(usuarioService.tienePermiso(P_ASIGNAR_ROL)) {
                usuario.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
            } else {
                usuario.setRol(rolService.existeRol(1L)); // ID 1: Rol sin permisos.
            }

            usuarioService.guardar(usuario);

            attributes.addFlashAttribute("message", "¡Usuario creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);
        Usuario usuario;

        // validar el id
        try {
            Long idUsuario = Long.parseLong(id);
            usuario = usuarioService.existeUsuario(idUsuario);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        model.addAttribute("user", usuario);

        // validar si puede cambiar de rol
        if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }
        model.addAttribute("permisoAsignarRol", asignarRol);

        if(eliminar) {
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("usuario") UsuarioDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Usuario usuario;

        if (result.hasErrors()) {
//            studentDTO.setId(id);
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            usuario = usuarioService.existeUsuario(id);
            if(usuario != null) {
                usuarioService.convertirDTO(usuario, objetoDTO);

                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.
                if(usuarioService.tienePermiso(P_ASIGNAR_ROL)) {
                    usuario.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
                }

                attributes.addFlashAttribute("message", "¡Usuario actualizado correctamente!");
                usuarioService.guardar(usuario);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("/{id}/delete")
        public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes  ) {
        this.operacion = "eliminar-";
        Long idUsuario;

        try {
            idUsuario = Long.parseLong(id);
        } catch (Exception e) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
            usuarioService.eliminarUsuario(usuario);
            attributes.addFlashAttribute("message", "¡Usuario eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

//    @GetMapping("asignar-rol")
//    public String asignarRolUsuario() {
//        this.operacion = "asignar-rol-";
//
//        if(usuarioService.tienePermiso(operacion + VIEW)) {
//            if(usuarioService.tienePermiso(operacion + VIEW)) {
//                return ASIGNAR_ROL_VIEW;
//            }
//        }
//        return FALTA_PERMISO_VIEW;
//    }

//    @PostMapping("asignar-rol")
//    public String asignarRolUsuarioForm(@RequestParam("id_rol") Long idRol, @RequestParam("username") String email) {
//        this.operacion = "asignar-rol-";
//
//        if(usuarioService.tienePermiso(operacion + VIEW)) {
//            Usuario usuario = usuarioService.existeUsuario(email);
//            Rol rol = rolService.existeRol(idRol);
//
//            if(usuario != null && rol != null) {
//                usuario.setRol(rol);
//                usuarioService.guardar(usuario);
//                return RD_ASIGNAR_ROL_VIEW;
//            }
//        }
//        return RD_FALTA_PERMISO_VIEW;
//    }

}
