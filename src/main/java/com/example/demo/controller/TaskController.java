package com.example.demo.controller;

import com.example.demo.dto.TaskDto;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.models.Task;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/api/task")
@RestController
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper taskMapper;

    @GetMapping
    List<TaskDto> GetTask() throws TaskNotFoundException {
        List<Task> tasks = taskService.getTasks();
        List<TaskDto> taskListDto = new ArrayList<>();

        for (Task task : tasks) {
            TaskDto taskDto = taskMapper.map(task, TaskDto.class);
            taskListDto.add(taskDto);
        }
        if (taskListDto.isEmpty()) {
            throw new TaskNotFoundException("Список задач пуст");
        }

        return taskListDto;
    }
}
