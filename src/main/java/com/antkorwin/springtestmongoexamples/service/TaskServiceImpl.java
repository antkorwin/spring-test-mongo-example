package com.antkorwin.springtestmongoexamples.service;

import com.antkorwin.commonutils.exceptions.InternalException;
import com.antkorwin.springtestmongoexamples.model.Task;
import com.antkorwin.springtestmongoexamples.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created on 01.01.2019.
 *
 * @author Korovin Anatoliy
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task get(String id) {
        return taskRepository.findById(id)
                             .orElseThrow(() -> new InternalException("oops", 1001));
    }

    @Override
    public Task create(String title, int estimate) {
        return taskRepository.save(Task.builder()
                                       .title(title)
                                       .estimate(estimate)
                                       .createTime(new Date())
                                       .build());
    }

    @Override
    public void delete(String id) {
        taskRepository.deleteById(id);
    }
}
