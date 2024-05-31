package com.example;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}
