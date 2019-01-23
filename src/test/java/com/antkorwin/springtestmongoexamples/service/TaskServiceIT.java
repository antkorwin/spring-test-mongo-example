package com.antkorwin.springtestmongoexamples.service;

import com.antkorwin.springtestmongo.annotation.ExpectedMongoDataSet;
import com.antkorwin.springtestmongo.annotation.ExportMongoDataSet;
import com.antkorwin.springtestmongo.annotation.MongoDataSet;
import com.antkorwin.springtestmongo.junit5.meta.annotation.MongoDbIntegrationTest;
import com.antkorwin.springtestmongoexamples.model.Task;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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

    @Disabled("use it just to generate an initial dataset")
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
    @MongoDataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedMongoDataSet("dataset/email.json")
    void email() {
        taskService.create("antkorwin@gmail.com", 123);
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

    @Test
    @MongoDataSet(value = "dataset/get_task.json", cleanBefore = true, cleanAfter = true)
    @ExpectedMongoDataSet(value = "dataset/delete_task_expected.json")
    void delete() {
        taskService.delete("5c2bfcf442a9171e16d5f0f0");
    }

    @Test
    @MongoDataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedMongoDataSet("dataset/create_task_groovy_expected.json")
    void groovy() {
        taskService.create("black magic",
                           1 + 2 + 3 + 4 + 5 + 6 + 7);
    }

    @Test
    @MongoDataSet(cleanBefore = true, cleanAfter = true,
                  value = "dataset/init_task_groovy.json")
    void groovyInitTest() {
        Task task = taskService.get("55f3ed00b1375a48e618300a");
        assertThat(task).extracting(Task::getEstimate, Task::getCreateTime)
                        .contains(1 + 2 + 3 + 4 + 5, new Date(12345));
    }
}