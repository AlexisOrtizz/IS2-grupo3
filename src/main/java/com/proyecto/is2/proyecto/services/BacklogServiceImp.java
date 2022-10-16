package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.model.Backlog;
import com.proyecto.is2.proyecto.repository.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BacklogServiceImp implements BacklogService {

    @Autowired
    private BacklogRepository backlogRepository;

    /**
     * Persiste un objeto del tipo Backlog
     *
     * @param backlog objeto a persistir
     * @return el objeto persistido
     */
    @Override
    public Backlog guardar(Backlog backlog) {
        return backlogRepository.save(backlog);
    }

    /**
     * @param backlog
     */
    @Override
    public void eliminar(Backlog backlog) {
        backlogRepository.delete(backlog);
    }
}
