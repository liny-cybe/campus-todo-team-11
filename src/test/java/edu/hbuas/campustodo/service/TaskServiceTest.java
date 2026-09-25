package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Priority;
import edu.hbuas.campustodo.model.Task;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @Test
    void shouldAddTask() {
        TaskService service = new TaskService();
        var task = service.addTask("完成需求评审");
        assertEquals(1L, task.getId());
        assertEquals("完成需求评审", task.getTitle());
        assertFalse(task.isCompleted());
        assertEquals(1, service.listAll().size());
    }

    @Test
    void shouldRejectBlankTitle() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class,
            () -> service.addTask(" "));
    }

    @Test
    void testFilterByPriority() {
        TaskService service = new TaskService();

        Task t1 = service.addTask("高数作业");
        t1.setPriority(Priority.HIGH);

        Task t2 = service.addTask("英语作业");
        // 默认 MEDIUM，无需手动设置

        Task t3 = service.addTask("整理桌面");
        t3.setPriority(Priority.LOW);

        // 筛选高优先级，预期1条
        List<Task> highList = service.filterByPriority(Priority.HIGH);
        assertEquals(1, highList.size());

        // 筛选默认中等优先级，预期1条
        List<Task> mediumList = service.filterByPriority(Priority.MEDIUM);
        assertEquals(1, mediumList.size());

        // 传入null，返回空列表，不能返回null
        List<Task> noMatch = service.filterByPriority(null);
        assertNotNull(noMatch);
        assertEquals(0, noMatch.size());
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
