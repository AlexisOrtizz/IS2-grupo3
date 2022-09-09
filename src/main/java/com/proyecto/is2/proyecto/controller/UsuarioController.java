package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar CRUD sobre Usuario
 */
@Controller
@RequestMapping("usuario")
public class UsuarioController implements CRUD<UsuarioDTO>{
    final String VIEW = "usuario";
    String operacion = "";
    final String FORM_VIEW = VIEW + "/form";
    final String RD_FORM_VIEW = "redirect:/" + FORM_VIEW;
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String LISTA_VIEW = VIEW + "/listar";
    final String ASIGNAR_ROL_VIEW = VIEW + "/asignar-rol";
    final String RD_ASIGNAR_ROL_VIEW = "redirect:/" + ASIGNAR_ROL_VIEW;
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

    @Override
    @GetMapping("form")
    public String mostrarCRUDTemplate(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);
        boolean asignarRol = usuarioService.tienePermiso("asignar-rol-" + VIEW);

        if(!crear && !eliminar && !actualizar) {
            return FALTA_PERMISO_VIEW;
        }

        if(actualizar || eliminar) {
            model.addAttribute("idUsuarios", usuarioService.listarUsuarios());
        }

        if(asignarRol) {
            model.addAttribute("roles", rolService.listar());
        }

        model.addAttribute("crear", crear);
        model.addAttribute("eliminar", eliminar);
        model.addAttribute("actualizar", actualizar);
        model.addAttribute("asignarRol", asignarRol);

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
    public String crearObjeto(@ModelAttribute("usuario") UsuarioDTO objetoDTO) {
        this.operacion = "crear-";

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
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @Override
    @PostMapping("eliminar")
        public String eliminarObjeto(@RequestParam("id_usuario") Integer id) {
        this.operacion = "eliminar-";


        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Usuario usuario = usuarioService.obtenerUsuario(id.longValue());
            usuarioService.eliminarUsuario(usuario);
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @Override
    @PostMapping("actualizar")
    public String actualizarObjeto(@ModelAttribute("usuario") UsuarioDTO objetoDTO) {
        this.operacion = "actualizar-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Usuario usuario = usuarioService.existeUsuario(objetoDTO.getEmail());
            if(usuario != null) {
                usuarioService.convertirDTO(usuario, objetoDTO);

                // si tiene permiso se le asigna el rol con id del formulario
                // sino se queda con el mismo rol.
                if(usuarioService.tienePermiso(P_ASIGNAR_ROL)) {
                    usuario.setRol(rolService.existeRol(objetoDTO.getIdRol().longValue()));
                }

                usuarioService.guardar(usuario);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

    @GetMapping("asignar-rol")
    public String asignarRolUsuario() {
        this.operacion = "asignar-rol-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            if(usuarioService.tienePermiso(operacion + VIEW)) {
                return ASIGNAR_ROL_VIEW;
            }
        }
        return FALTA_PERMISO_VIEW;
    }

    @PostMapping("asignar-rol")
    public String asignarRolUsuarioForm(@RequestParam("id_rol") Long idRol, @RequestParam("username") String email) {
        this.operacion = "asignar-rol-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Usuario usuario = usuarioService.existeUsuario(email);
            Rol rol = rolService.existeRol(idRol);

            if(usuario != null && rol != null) {
                usuario.setRol(rol);
                usuarioService.guardar(usuario);
                return RD_ASIGNAR_ROL_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

}
