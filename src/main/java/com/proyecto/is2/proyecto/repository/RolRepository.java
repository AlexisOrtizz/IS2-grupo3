package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {

    public Rol findByIdRol(Long idRol);

}
