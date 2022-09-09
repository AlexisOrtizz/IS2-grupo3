package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.RolDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
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

    @PostMapping("asignar-permisos-rol")
    public String asignarPermisoRol(@RequestParam("idRol") Long idRol, @RequestParam("lista") List<Long> idPermisos) {
        this.operacion = "asignar-permisos-";

        // verifica el permiso
        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Rol rol = rolService.existeRol(idRol);

            // itera sobre los permisos si se encontro el rol
            if(rol != null) {
                for(Long id : idPermisos) {
                    Permiso permiso = permisoService.existePermiso(id);

                    // agrega el permiso si es valido al rol
                    if(permiso != null) {
                        rol.getPermisos().add(permiso);
                    }
                }
                rolService.guardar(rol);
            }
            return ASIGNAR_ROL_VIEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

}
