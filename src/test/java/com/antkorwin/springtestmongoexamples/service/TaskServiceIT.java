package com.antkorwin.springtestmongoexamples.service;

import com.antkorwin.springtestmongo.annotation.ExpectedMongoDataSet;
import com.antkorwin.springtestmongo.annotation.ExportMongoDataSet;
import com.antkorwin.springtestmongo.annotation.MongoDataSet;
import com.antkorwin.springtestmongo.junit5.meta.annotation.MongoDbIntegrationTest;
import com.antkorwin.springtestmongoexamples.model.Task;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 01.01.2019.
 *
 * @author Korovin Anatoliy
 */
@MongoDbIntegrationTest
class TaskServiceIT {

    @Autowired
    private TaskService taskService;

    @Disabled("use just to generate test dataset")
    @Test
    @ExportMongoDataSet(outputFile = "./target/datasets/create_task_expected.json")
    void generate() {
        taskService.create("black magic", 123);
    }

    @Test
    @MongoDataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedMongoDataSet("dataset/create_task_expected.json")
    void create() {
        taskService.create("black magic", 123);
    }

    @Test
    @MongoDataSet(value = "dataset/get_task.json", cleanBefore = true, cleanAfter = true)
    void get() {
        // Act
        Task task = taskService.get("5c2bfcf442a9171e16d5f0f0");
        // Asserts
        assertThat(task).isNotNull()
                        .extracting(Task::getTitle, Task::getEstimate)
                        .containsOnly("black magic", 1987);
    }
}