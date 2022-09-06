package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.RolDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.services.PermisoServiceImp;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RolController implements CRUD<RolDTO> {
    final String IDENTIFICADOR = "rol";
    String operacion = "";

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

    @Override
    public String mostrarCRUDTemplate(Model model) {
        Set<Permiso> permisos = usuarioService.verPermisosUsuarioActual();

        model.addAttribute("permisos", permisos);

        return "rol/form";
    }

    @Override
    public String mostrarObjetos() {
        this.operacion = "mostrar-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)){
            return "usuario/lista";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    public String crearObjeto(@ModelAttribute("rol") RolDTO objetoDTO) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Rol rol = rolService.convertirDTO(objetoDTO);
            rolService.guardar(rol);
            return "rol/form";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    public String eliminarObjeto(Long id) {
        this.operacion = "eliminar-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Rol rol = rolService.existeRol(id);
            rolService.eliminarRol(rol);
            return "rol/form";
        } else {
            return "falta-permiso";
        }
    }

    @Override
    public String actualizarObjeto(RolDTO objetoDTO) {
        this.operacion = "actualizar-";

        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
            Rol rol = rolService.convertirDTO(objetoDTO);
            rolService.guardar(rol);
            return "rol/form";
        } else {
            return "falta-permiso";
        }
    }

    @PostMapping("asignar-permisos-rol")
    public String asignarPermisoRol(@RequestParam("idRol") Long idRol, @RequestParam("lista") List<Long> idPermisos) {
        this.operacion = "asignar-permisos-";

        // verifica el permiso
        if(usuarioService.tienePermiso(operacion + IDENTIFICADOR)) {
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
            return "rol/form";
        } else {
            return "falta-permiso";
        }
    }

}
