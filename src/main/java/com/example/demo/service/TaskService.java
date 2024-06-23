package com.example.demo.service;

import com.example.demo.dto.TaskResponseDto;
import com.example.demo.exception.TaskCreateException;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.models.Task;
import com.example.demo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    public Long addNewTask(Task newTask) {
        if(newTask.getTitle().strip().isEmpty()){
            throw new TaskCreateException("Поле заголовка не должно быть пустым");
        }
        if(newTask.getDescription().strip().isEmpty()){
            throw new TaskCreateException("Поле описания не должно быть пустым");
        }
        return taskRepository.save(newTask).getId();
    }

    public Task findTaskById(long taskId) {
        
        return taskRepository.findById(taskId).orElseThrow(() ->new TaskNotFoundException(
                "Задача с id = "+ taskId +" не существует"
        ));
    }
    
    public Long deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() ->new TaskNotFoundException(
                "Задача с id = "+ id +" не существует"
        ));
        taskRepository.deleteById(id);
        return id;
    }

    @Transactional
    public void switchCompleted(Long taskId) {
        Task task = findTaskById(taskId);
        task.setCompleted(!task.getCompleted());
        taskRepository.save(task);
    }
}
