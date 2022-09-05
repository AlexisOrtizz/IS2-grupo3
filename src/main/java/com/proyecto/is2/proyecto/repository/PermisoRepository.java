package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    public Permiso findByIdPermiso(Long idPermiso);

}
