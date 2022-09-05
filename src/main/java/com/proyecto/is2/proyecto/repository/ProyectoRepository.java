package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    public Proyecto findByIdProyecto(Long idProyecto);

}
