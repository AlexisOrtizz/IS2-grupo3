package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.Vista;

import java.util.List;

public interface VistaService {

    /**
     * Persiste un objeto del tipo Usuario
     * @param vista objeto a persistir
     * @return el objeto persistido
     */
    public Vista guardar(Vista vista);

    /**
     * Lista todos los roles que existen
     * @return
     */
    public List<Vista> listar();

}
