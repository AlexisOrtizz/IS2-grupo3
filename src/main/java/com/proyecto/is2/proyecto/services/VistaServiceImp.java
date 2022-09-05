package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.Vista;
import com.proyecto.is2.proyecto.repository.VistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VistaServiceImp implements VistaService {

    @Autowired
    private VistaRepository vistaRepository;

    /**
     * Se crean las vistas por defectos que existen
     */
    public void crearVistas() {
        List<Vista> vistas = new ArrayList<>();

        vistas.add(new Vista("usuario"));
        vistas.add(new Vista("proyecto"));
        vistas.add(new Vista("backlog"));
        vistas.add(new Vista("sprint"));
        vistas.add(new Vista("user-story"));
        vistas.add(new Vista("rol"));
        vistas.add(new Vista("permiso"));
        vistas.add(new Vista("vista"));

        vistaRepository.saveAll(vistas);
    }

    @Override
    public Vista guardar(Vista vista) {
        return vistaRepository.save(vista);
    }

    @Override
    public List<Vista> listar() {
        return vistaRepository.findAll();
    }
}
