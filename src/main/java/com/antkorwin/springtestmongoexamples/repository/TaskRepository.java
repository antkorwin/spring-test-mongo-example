package com.antkorwin.springtestmongoexamples.repository;

import com.antkorwin.springtestmongoexamples.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created on 01.01.2019.
 *
 * @author Korovin Anatoliy
 */
public interface TaskRepository extends MongoRepository<Task, String> {
}
