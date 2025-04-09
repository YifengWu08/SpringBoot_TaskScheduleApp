package com.yifeng.tasks.mappers;

import com.yifeng.tasks.domain.dto.TaskListDto;
import com.yifeng.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
