package com.proyecto.is2.proyecto.services;


import com.proyecto.is2.proyecto.model.Backlog;

import java.util.List;

public interface BacklogService {
    /**
     * Persiste un objeto del tipo Backlog
     * @param backlog objeto a persistir
     * @return el objeto persistido
     */
    public Backlog guardar(Backlog backlog);

    public void eliminar(Backlog backlog);
}
