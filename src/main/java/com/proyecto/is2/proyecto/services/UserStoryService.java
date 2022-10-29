package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.StoryDTO;
import com.proyecto.is2.proyecto.model.UserStory;

import java.util.List;

public interface UserStoryService {
    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Entity
     * @param objeto los datos de tabla
     * @param objetoDTO los datos del formulario
     * @return el objeto Proyecto creado
     */
    public void convertirDTO(UserStory objeto, StoryDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Entity
     * @param objeto objeto a persistir
     * @return el objeto persistido
     */
    public UserStory guardar(UserStory objeto);

    public void eliminar(UserStory objeto);

    /**
     * Lista todos los registros creados
     * @return
     */
    public List<UserStory> listar();

    /**
     * Realiza una busqueda entre los registros creados
     * @param id identificador del objeto a buscar
     * @return objeto encontrado o null.
     */
    public UserStory existeObjeto(Long id);
}
