package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.ProyectoDTO;
import com.proyecto.is2.proyecto.model.Backlog;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProyectoServiceImp implements ProyectoService {

    @Override
    public void convertirDTO(Proyecto proyecto, ProyectoDTO objetoDTO) {
        proyecto.setTitulo(objetoDTO.getTitulo());
        proyecto.setDescripcion(objetoDTO.getDescripcion());
        proyecto.setObservacion(objetoDTO.getObservacion());
        proyecto.setEstado(objetoDTO.getEstado());
        try {
            proyecto.setFechaInicio(LocalDate.parse(objetoDTO.getFechaInicio(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            proyecto.setFechaFin(LocalDate.parse(objetoDTO.getFechaFin(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } catch (Exception e) {
            proyecto.setFechaInicio(null);
            proyecto.setFechaFin(null);
        }
        return;
    }

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private BacklogServiceImp backlogService;

    @Override
    public Proyecto guardar(Proyecto proyecto) {
        // si no hay un backlog, crear uno por defecto
        if(proyecto.getBacklog() == null) {
            Backlog backlog = backlogService.guardar(new Backlog(proyecto.getTitulo(), proyecto.getDescripcion()));
            proyecto.setBacklog(backlog);
        }

        Proyecto project = proyectoRepository.save(proyecto);
        return project;
    }

    @Override
    public void eliminar(Proyecto proyecto) {
        proyectoRepository.delete(proyecto);
    }

    @Override
    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    @Override
    public Proyecto existeProyecto(Long id) {
        return proyectoRepository.findByIdProyecto(id);
    }

}
