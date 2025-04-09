package com.yifeng.tasks.mappers;

import com.yifeng.tasks.domain.dto.TaskDto;
import com.yifeng.tasks.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
