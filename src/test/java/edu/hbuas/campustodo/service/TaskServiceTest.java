package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void shouldAddTask() {
        var task = taskService.addTask("完成需求评审");
        assertEquals(1L, task.getId());
        assertEquals("完成需求评审", task.getTitle());
        assertFalse(task.isCompleted());
        assertEquals(1, taskService.listAll().size());
    }

    @Test
    void shouldRejectBlankTitle() {
        assertThrows(IllegalArgumentException.class,
            () -> taskService.addTask(" "));
    }

    @Test
    void testCompleteTask_Success() {
        Task task = taskService.addTask("复习软件工程");
        taskService.completeTask(task.getId());
        assertTrue(task.isCompleted(), "任务应被标记为已完成");
    }

    @Test
    void testCompleteTask_TaskNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.completeTask(999L);
        }, "不存在的任务编号应抛出异常");
    }

    @Test
    void testCompleteTask_AlreadyCompleted() {
        Task task = taskService.addTask("写实验报告");
        taskService.completeTask(task.getId());
        assertThrows(IllegalStateException.class, () -> {
            taskService.completeTask(task.getId());
        }, "重复完成同一任务应抛出异常");
    }
}
