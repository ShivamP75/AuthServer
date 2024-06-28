package com.security.authenticationserver.controller;

import com.security.authenticationserver.models.Task;
import com.security.authenticationserver.models.ToDoEntity;
import com.security.authenticationserver.models.ToDoTaskDTO;
import com.security.authenticationserver.repository.TODOTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.logging.Logger;

@RestController
public class ToDoController {

    Logger logger = Logger.getLogger(ToDoController.class.getName());

    @Autowired
    private TODOTaskRepo todoTaskRepo;

    @PostMapping("add-task")
    public ResponseEntity<?> addTask(@RequestBody ToDoEntity toDoEntity) {
        toDoEntity.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ToDoTaskDTO existingUser = todoTaskRepo.findByUsername(toDoEntity.getUsername());

       if (existingUser == null) {
           existingUser = new ToDoTaskDTO();
           existingUser.setUsername(toDoEntity.getUsername());
           existingUser.setTasks(new ArrayList<>());
       }
        Task task = new Task(toDoEntity.getTask());
        existingUser.getTasks().add(task);
        ToDoTaskDTO newTask = todoTaskRepo.save(existingUser);
        logger.info("Task added: " + newTask);
        return ResponseEntity.ok(newTask);
    }
    @GetMapping("get-task")
    public ResponseEntity<?> getTask() {
        ToDoTaskDTO existingUser = todoTaskRepo.
                findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (existingUser == null) {
            return ResponseEntity.status(404).body("No task found with username : " +
                    SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return ResponseEntity.ok(existingUser.getTasks());
    }

    @DeleteMapping("delete-task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        ToDoTaskDTO toDoTask = todoTaskRepo.
                findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (toDoTask == null) {
            return ResponseEntity.status(404).body("No task found with username : " +
                    SecurityContextHolder.getContext().getAuthentication().getName());
        }
        boolean isIdPresent = false;
        for (int i = 0; i < toDoTask.getTasks().size(); i++) {
            if (toDoTask.getTasks().get(i).getId() == id) {
                toDoTask.getTasks().remove(i);
                isIdPresent = true;
                break;
            }
        }
        if (!isIdPresent) {
            return ResponseEntity.badRequest().body("No task present for id: " + id);
        }
        ToDoTaskDTO newTask = todoTaskRepo.save(toDoTask);
        logger.info("Task deleted for id: " + id);
        return ResponseEntity.ok(newTask);
    }

    @PutMapping("update-task/{id}")
    public ResponseEntity<?> updateTask(@RequestBody ToDoEntity toDoEntity, @PathVariable long id) {
        ToDoTaskDTO toDoTask = todoTaskRepo.
                findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (toDoTask == null) {
            return ResponseEntity.status(404).body("No task found with username : " +
                    SecurityContextHolder.getContext().getAuthentication().getName());
        }
        boolean isIdPresent = false;
        for (int i = 0; i < toDoTask.getTasks().size(); i++) {
            if (toDoTask.getTasks().get(i).getId() == id) {
                toDoTask.getTasks().set(i , new Task(toDoEntity.getTask()));
                isIdPresent = true;
                break;
            }
        }
        if (!isIdPresent) {
            return ResponseEntity.badRequest().body("No task present for id: " + id);
        }
        ToDoTaskDTO newTask = todoTaskRepo.save(toDoTask);
        logger.info("Task with id " + id + " has been updated");
        return ResponseEntity.ok(newTask);
    }

}
