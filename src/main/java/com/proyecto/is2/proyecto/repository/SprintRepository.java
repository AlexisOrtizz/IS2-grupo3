package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Backlog;
import com.proyecto.is2.proyecto.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    public Sprint findByIdSprint(Long idSprint);

    public List<Sprint> findByBacklogOrderByIdSprintAsc(Backlog backlog);
}
