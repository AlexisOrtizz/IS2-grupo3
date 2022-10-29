package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.SprintDTO;
import com.proyecto.is2.proyecto.model.Sprint;

import java.util.List;

public interface SprintService {
    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Entity
     * @param objeto los datos de tabla
     * @param objetoDTO los datos del formulario
     * @return el objeto Proyecto creado
     */
    public void convertirDTO(Sprint objeto, SprintDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Entity
     * @param objeto objeto a persistir
     * @return el objeto persistido
     */
    public Sprint guardar(Sprint objeto);

    public void eliminar(Sprint objeto);

    /**
     * Lista todos los registros creados
     * @return
     */
    public List<Sprint> listar();

    /**
     * Realiza una busqueda entre los registros creados
     * @param id identificador del objeto a buscar
     * @return objeto encontrado o null.
     */
    public Sprint existeObjeto(Long id);
}
