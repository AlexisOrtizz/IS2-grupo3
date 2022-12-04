package com.proyecto.is2.proyecto.controller;

import com.proyecto.is2.proyecto.controller.dto.PermisoDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.services.PermisoServiceImp;
import com.proyecto.is2.proyecto.services.UsuarioServiceImp;
import com.proyecto.is2.proyecto.services.VistaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("permisos")
public class PermisoController {
    final String VIEW = "permiso";
    final String VIEW_PATH = "permiso";
    String operacion = "";
    final String FORM_VIEW = VIEW + "/permisos";
    final String FORM_NEW = VIEW_PATH + "/nuevo";
    final String FORM_EDIT = VIEW_PATH + "/editar";
    final String RD_FORM_VIEW = "redirect:/permisos";
    final String FALTA_PERMISO_VIEW = "falta-permiso";
    final String RD_FALTA_PERMISO_VIEW = "redirect:/" + FALTA_PERMISO_VIEW;
    final String LISTA_VIEW = VIEW + "/listar";

    @Autowired
    private VistaServiceImp vistaService;

    @Autowired
    private PermisoServiceImp permisoService;

    @Autowired
    private UsuarioServiceImp usuarioService;

    @ModelAttribute("modelPermiso")
    public PermisoDTO instanciarDTO() {
        return new PermisoDTO();
    }

    @GetMapping
    public String mostrarCRUDTemplate(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        boolean actualizar = usuarioService.tienePermiso("actualizar-" + VIEW);
        boolean consultar = usuarioService.tienePermiso("consultar-" + VIEW);

        if(consultar) {
            model.addAttribute("listPermisos", permisoService.listar());
        } else {
            return FALTA_PERMISO_VIEW;
        }

        model.addAttribute("permisoCrear", crear);
        model.addAttribute("permisoVer", consultar);
        model.addAttribute("permisoEliminar", eliminar);
        model.addAttribute("permisoActualizar", actualizar);

        return FORM_VIEW;
    }

    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        boolean crear = usuarioService.tienePermiso("crear-" + VIEW);

        if(crear) {
            model.addAttribute("vistas", vistaService.listar());
            return FORM_NEW;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/crear")
    public String crearObjeto(@ModelAttribute("permiso") PermisoDTO objetoDTO,
                              RedirectAttributes attributes) {
        this.operacion = "crear-";

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Permiso obj = new Permiso();
            permisoService.convertirDTO(obj, objetoDTO);
            permisoService.guardar(obj);

            attributes.addFlashAttribute("message", "¡Permiso creado exitosamente!");

            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}/delete")
    public String eliminarObjeto(@PathVariable String id, RedirectAttributes attributes) {
        this.operacion = "eliminar-";
        Long idPer;

        try {
            idPer = Long.parseLong(id);
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "¡Id permiso no valido!");
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            Permiso obj = permisoService.existePermiso(idPer);
            try {
                permisoService.eliminarPermiso(obj);
            } catch (Exception e){
                attributes.addFlashAttribute("message", "No se ha podido eliminar el permiso, Verifique que no vaya roles con este permiso.");
                return RD_FORM_VIEW;
            }
            attributes.addFlashAttribute("message", "Permiso eliminado correctamente!");
            return RD_FORM_VIEW;
        } else {
            return RD_FALTA_PERMISO_VIEW;
        }
    }

    @GetMapping("/{id}")
    public String formEditar(@PathVariable String id, Model model) {
        boolean eliminar = usuarioService.tienePermiso("eliminar-" + VIEW);
        Permiso per;

        // validar el id
        try {
            Long idPer = Long.parseLong(id);
            per = permisoService.existePermiso(idPer);
        } catch(Exception e) {
            return RD_FORM_VIEW;
        }

        if(eliminar) {
            model.addAttribute("permiso", per);
            model.addAttribute("vistas", vistaService.listar());
            return FORM_EDIT;
        } else {
            return FALTA_PERMISO_VIEW;
        }
    }

    @PostMapping("/{id}")
    public String actualizarObjeto(@PathVariable Long id, @ModelAttribute("permiso") PermisoDTO objetoDTO,
                                   BindingResult result, RedirectAttributes attributes) {
        this.operacion = "actualizar-";
        Permiso per;

        if (result.hasErrors()) {
            return RD_FORM_VIEW;
        }

        if(usuarioService.tienePermiso(operacion + VIEW)) {
            per = permisoService.existePermiso(id);
            if(per != null) {
                permisoService.convertirDTO(per, objetoDTO);
                attributes.addFlashAttribute("message", "¡Permiso actualizado correctamente!");
                permisoService.guardar(per);
                return RD_FORM_VIEW;
            } else {
                attributes.addFlashAttribute("message", "¡Id del permiso no existe!");
                return RD_FORM_VIEW;
            }
        }
        return RD_FALTA_PERMISO_VIEW;
    }

}
