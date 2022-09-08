package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controlador encargado de recibir las peticiones
 * para realizar CRUD sobre Usuario
 */
@Controller
@RequestMapping("usuario")
public class UsuarioController implements CRUD<UsuarioDTO>{
    final String IDENTIFICADOR = "usuario";
    String operacion = "";

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
    @GetMapping("/form")
    public String mostrarCRUDTemplate(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-usuario");
        boolean eliminar = usuarioService.tienePermiso("eliminar-usuario");
        boolean actualizar = usuarioService.tienePermiso("actualizar-usuario");

        if(eliminar) {
            model.addAttribute("idUsuarios", usuarioService.listarUsuarios());
        }

        model.addAttribute("crear", crear);
        model.addAttribute("eliminar", eliminar);
        model.addAttribute("actualizar", actualizar);

        return "usuario/form";
    }

    @Override
    @GetMapping("/lista")
    public String mostrarObjetos() {
        this.operacion = "mostrar-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)){
            return "usuario/lista";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("usuario") UsuarioDTO objetoDTO) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Usuario usuario = usuarioService.convertirDTO(objetoDTO);
            usuarioService.guardar(usuario);
            return "usuario/form";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    @PostMapping("/eliminar")
        public String eliminarObjeto(@RequestParam("id_usuario") Integer id) {
        this.operacion = "eliminar-";


        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Usuario usuario = usuarioService.obtenerUsuario(id.longValue());
            usuarioService.eliminarUsuario(usuario);
            return "usuario/form";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    @PostMapping("/actualizar")
    public String actualizarObjeto(@ModelAttribute("usuario") UsuarioDTO objetoDTO) {
        this.operacion = "actualizar-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Usuario usuario = usuarioService.convertirDTO(objetoDTO);
            usuarioService.guardar(usuario);
            return "usuario/form";
        } else {
            return "falta-permiso";
        }
    }

    @PostMapping("asignar-rol")
    public String asignarRolUsuario(@RequestParam("id_rol") Long idRol, @RequestParam("username") String email) {
        this.operacion = "asignar-roles-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Usuario usuario = usuarioService.existeUsuario(email);
            Rol rol = rolService.existeRol(idRol);

            if(usuario != null && rol != null) {
                usuario.setRol(rol);
                usuarioService.guardar(usuario);
                return "usuario/asignar-rol";
            }
        }
        return "falta-permiso";
    }

}
