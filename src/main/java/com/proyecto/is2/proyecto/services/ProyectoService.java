package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.ProyectoDTO;
import com.proyecto.is2.proyecto.model.Proyecto;
import com.proyecto.is2.proyecto.model.Usuario;

import java.util.List;

public interface ProyectoService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Proyecto
     * @param objetoDTO los datos del formulario
     * @return el objeto Proyecto creado
     */
    public void convertirDTO(Proyecto proyecto, ProyectoDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Proyecto
     * @param proyecto objeto a persistir
     * @return el objeto persistido
     */
    public Proyecto guardar(Proyecto proyecto);

    public void eliminar(Proyecto proyecto);

    /**
     * Lista todos los proyectos creados
     * @return
     */
    public List<Proyecto> listarProyectos();

    /**
     * Realiza una busqueda entre los proyectos creados
     * @param id identificador del proyecto a buscar
     * @return Proyecto encontrado o null.
     */
    public Proyecto existeProyecto(Long id);

    /**
     * Agregar un nuevo miembro al equipo del Proyecto.
     * @param proyecto
     * @param usuario miembro a agregar al proyecto.
     * @return boolean

    public boolean agregarMiembro(Proyecto proyecto,  Usuario usuario);

    /**
     * Eliminar un miembro del equipo del Proyecto.
     * @param proyecto
     * @param usuario miembro a eliminar del proyecto.
     * @return boolean

    public boolean eliminarMiembro(Proyecto proyecto, Usuario usuario);
    */
}
