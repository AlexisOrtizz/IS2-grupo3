package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.PermisoDTO;
import com.proyecto.is2.proyecto.controller.dto.RolDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.services.PermisoServiceImp;
import com.proyecto.is2.proyecto.services.RolServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import com.proyecto.is2.proyecto.services.VistaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("permiso")
public class PermisoController implements CRUD<PermisoDTO> {
    final String VIEW = "permiso";
    String operacion = "";
    final String FORM_VIEW = VIEW + "/form";
    final String RD_FORM_VIEW = "redirect:/" + FORM_VIEW;
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String LISTA_VIEW = VIEW + "/listar";

    @Autowired
    private VistaServiceImp vistaService;

    @Autowired
    private PermisoServiceImp permisoService;

    @Autowired
    private UsuarioServiceImp usuarioService;

    @ModelAttribute("listPermisos")
    public List<Long> listaPermisos() {
        return new ArrayList<>();
    }

    @ModelAttribute("permiso")
    public PermisoDTO instanciarObjetoDTO() {
        return new PermisoDTO();
    }

    @Override
    @GetMapping("form")
    public String mostrarCRUDTemplate(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);

        if(actualizar || eliminar) {
            model.addAttribute("idPermisos", permisoService.listar());
        } else {
            model.addAttribute("idPermisos", null);
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
    public String crearObjeto(@ModelAttribute("permiso") PermisoDTO objetoDTO) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Permiso permiso = new Permiso();
            permisoService.convertirDTO(permiso, objetoDTO);
            permisoService.guardar(permiso);
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @Override
    @PostMapping("eliminar")
    public String eliminarObjeto(@RequestParam("id_permiso") Integer id) {
        this.operacion = "eliminar-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Permiso permiso = permisoService.existePermiso(id.longValue());
            permisoService.eliminarPermiso(permiso);
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @Override
    @PostMapping("actualizar")
    public String actualizarObjeto(@ModelAttribute("permiso") PermisoDTO objetoDTO) {
        this.operacion = "actualizar-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Permiso permiso = permisoService.existePermiso(objetoDTO.getIdPermiso().longValue());
            if(permiso != null) {
                permisoService.convertirDTO(permiso, objetoDTO);
                permisoService.guardar(permiso);
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

}
