package com.yifeng.tasks.services.Impl;

import com.yifeng.tasks.domain.entities.Task;
import com.yifeng.tasks.domain.entities.TaskList;
import com.yifeng.tasks.domain.entities.TaskPriority;
import com.yifeng.tasks.domain.entities.TaskStatus;
import com.yifeng.tasks.repositories.TaskListRepository;
import com.yifeng.tasks.repositories.TaskRepository;
import com.yifeng.tasks.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTask(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already has an ID!");
        }

        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        LocalDateTime now = LocalDateTime.now();
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task Id provided"));

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );

        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListID, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListID, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListID, UUID taskId, Task task) {
        if (null == task.getId()) {
            throw new IllegalArgumentException("Task id cannot be empty!");
        }

        if (!Objects.equals(task.getId(), taskId)) {
            throw new IllegalArgumentException("not permitted to change task id!");
        }

        if (null == task.getPriority()) {
            throw new IllegalArgumentException("Task priority cannot be empty!");
        }

        if (null == task.getStatus()) {
            throw new IllegalArgumentException("Task status cannot be empty!");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListID, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task Id provided"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setUpdated(LocalDateTime.now());
        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListID, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListID, taskId);
    }


}
