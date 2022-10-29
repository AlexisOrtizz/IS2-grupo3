package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
    public UserStory findByIdHistoria(Long idHistoria);
}
