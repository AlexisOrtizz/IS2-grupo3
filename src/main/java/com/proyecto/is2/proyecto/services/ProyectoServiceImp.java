package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.ProyectoDTO;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServiceImp implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public Proyecto convertirDTO(ProyectoDTO objetoDTO) {
        Proyecto proyecto = new Proyecto();

        proyecto.setTitulo(objetoDTO.getTitulo());
        proyecto.setDescripcion(objetoDTO.getDescripcion());
        proyecto.setObservacion(objetoDTO.getObservacion());
        proyecto.setEstado(objetoDTO.getEstado());
        proyecto.setFechaInicio(objetoDTO.getFechaInicio());
        proyecto.setFechaInicio(objetoDTO.getFechaFin());

        return proyecto;
    }

    @Override
    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
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
