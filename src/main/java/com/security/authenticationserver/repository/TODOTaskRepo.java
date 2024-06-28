package com.security.authenticationserver.repository;

import com.security.authenticationserver.models.ToDoTaskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TODOTaskRepo extends JpaRepository<ToDoTaskDTO, Long> {

    ToDoTaskDTO findByUsername(String username);
}
