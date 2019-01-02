package com.antkorwin.springtestmongoexamples.service;

import com.antkorwin.springtestmongoexamples.model.Task;

/**
 * Created on 01.01.2019.
 *
 * @author Korovin Anatoliy
 */
public interface TaskService {

    Task get(String id);

    Task create(String title, int estimate);

    void delete(String id);
}
