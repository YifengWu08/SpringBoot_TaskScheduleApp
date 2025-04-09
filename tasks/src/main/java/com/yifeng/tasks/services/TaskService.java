package com.yifeng.tasks.services;

import com.yifeng.tasks.domain.entities.Task;
import com.yifeng.tasks.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTask(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    Optional<Task> getTask(UUID taskListID, UUID taskId);
    Task updateTask(UUID taskListID, UUID taskId, Task task);
    void deleteTask(UUID taskListID, UUID taskId);
}
