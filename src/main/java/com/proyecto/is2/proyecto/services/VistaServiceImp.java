package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Vista;
import com.proyecto.is2.proyecto.repository.VistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VistaServiceImp {

    @Autowired
    private VistaRepository vistaRepository;

    /**
     * Se crean las vistas por defectos que existen
     */
    public void crearVistas() {
        List<Vista> vistas = new ArrayList<>();

        vistas.add(new Vista(GeneralUtils.USER_CODE));
        vistas.add(new Vista(GeneralUtils.PROJECT_CODE));
        vistas.add(new Vista(GeneralUtils.BACKLOG_CODE));
        vistas.add(new Vista(GeneralUtils.SPRINT_CODE));
        vistas.add(new Vista(GeneralUtils.STORY_CODE));
        vistas.add(new Vista(GeneralUtils.ROL_CODE));
        vistas.add(new Vista(GeneralUtils.PERMISO_CODE));
        vistas.add(new Vista(GeneralUtils.VIEW_CODE));

        vistaRepository.saveAll(vistas);
    }

    public Vista guardar(Vista vista) {
        return vistaRepository.save(vista);
    }

    public List<Vista> listar() {
        return vistaRepository.findAll();
    }

    public Vista existeVista(Long id) {
        return vistaRepository.findByIdVista(id);
    }
}
