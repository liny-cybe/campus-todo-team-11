package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Priority;
import edu.hbuas.campustodo.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务应用服务。学生将在功能分支中逐步扩展该类。
 */
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public Task addTask(String title) {
        Task task = new Task(nextId++, title);
        tasks.add(task);
        return task;
    }

    public List<Task> listAll() {
        return List.copyOf(tasks);
    }

    // 新增：按优先级筛选任务
    public List<Task> filterByPriority(Priority priority) {
        return tasks.stream()
            .filter(task -> task.getPriority() == priority)
            .collect(Collectors.toList());
    }
    public void completeTask(long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                if (task.isCompleted()) {
                    throw new IllegalStateException("任务已完成，请勿重复操作");
                }
                task.complete();
                return;
            }
        }
        throw new IllegalArgumentException("找不到编号为 " + id + " 的任务");
    }
}
