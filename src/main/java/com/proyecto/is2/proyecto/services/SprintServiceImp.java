package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.SprintDTO;
import com.proyecto.is2.proyecto.model.Sprint;
import com.proyecto.is2.proyecto.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SprintServiceImp implements SprintService {
    @Override
    public void convertirDTO(Sprint objeto, SprintDTO objetoDTO) {
        objeto.setTitulo(objetoDTO.getTitulo());
        objeto.setDuracion(objetoDTO.getDuracion());
        objeto.setEstado(objetoDTO.getEstado());
        objeto.setFechaInicio(GeneralUtils.getDateToString(objetoDTO.getFechaInicio()));
        objeto.setFechaFin(GeneralUtils.getDateToString(objetoDTO.getFechaFin()));
    }

    @Autowired
    private SprintRepository sprintRepository;

    @Override
    public Sprint guardar(Sprint objeto) {
        return sprintRepository.save(objeto);
    }

    @Override
    public void eliminar(Sprint objeto) {
        sprintRepository.delete(objeto);
    }

    @Override
    public List<Sprint> listar() {
        return sprintRepository.findAll();
    }

    @Override
    public Sprint existeObjeto(Long id) {
        return sprintRepository.findByIdSprint(id);
    }
}
