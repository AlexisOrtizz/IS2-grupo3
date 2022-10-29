package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.Util.GeneralUtils;
import com.proyecto.is2.proyecto.controller.dto.StoryDTO;
import com.proyecto.is2.proyecto.model.UserStory;
import com.proyecto.is2.proyecto.repository.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserStoryServiceImp implements UserStoryService {

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Override
    public void convertirDTO(UserStory objeto, StoryDTO objetoDTO) {
        objeto.setDescripcion(objetoDTO.getDescripcion());
        objeto.setObservacion(objetoDTO.getObservacion());
        objeto.setEstado(objetoDTO.getEstado());
        objeto.setPrioridad(objetoDTO.getPrioridad());
    }

    @Override
    public UserStory guardar(UserStory objeto) {
        return userStoryRepository.save(objeto);
    }

    @Override
    public void eliminar(UserStory objeto) {
        userStoryRepository.delete(objeto);
    }

    @Override
    public List<UserStory> listar() {
        return userStoryRepository.findAll();
    }

    @Override
    public UserStory existeObjeto(Long id) {
        return userStoryRepository.findByIdHistoria(id);
    }
}
