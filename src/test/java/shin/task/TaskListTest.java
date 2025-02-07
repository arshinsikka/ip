package shin.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void testAddTask() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Buy groceries");
        taskList.addTask(task);
        assertEquals(1, taskList.size()); // ✅ Checks if the task was added successfully
    }

    @Test
    public void testRemoveTask() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Buy groceries");
        taskList.addTask(task);
        taskList.removeTask(0);
        assertEquals(0, taskList.size()); // ✅ Checks if task was removed
    }
}
