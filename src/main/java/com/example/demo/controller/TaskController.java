package com.example.demo.controller;

import com.example.demo.dto.TaskCreateDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.exception.TaskControllerExceptionHandler;
import com.example.demo.exception.TaskCreateException;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.models.Task;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/api/task")
@RestController
@TaskControllerExceptionHandler
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper taskMapper;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<TaskDto> GetTask() {
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

    @GetMapping(path = "{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTask(@PathVariable long taskId) {
        Task task = taskService.findTaskById(taskId);
        TaskDto taskDto = taskMapper.map(task, TaskDto.class);

        return taskDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskResponseDto CreateTask(@RequestBody TaskCreateDto taskCreateDto) {
        Task newTask = taskMapper.map(taskCreateDto, Task.class);

        var taskId = taskService.addNewTask(newTask);
        var taskCreationResponseDto = new TaskResponseDto().setId(taskId);

        return taskCreationResponseDto;
    }

    @DeleteMapping(path = "{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDto deleteTask(@PathVariable("taskId") Long id){
        TaskResponseDto taskResponseDto = new TaskResponseDto().setId(taskService.deleteTask(id));
        
        return taskResponseDto;
    }
    
    @PutMapping(path = "{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto patchTask (@PathVariable("taskId") Long taskId) {
        taskService.switchCompleted(taskId);
        return taskMapper.map(taskService.findTaskById(taskId), TaskDto.class);
    }

}
