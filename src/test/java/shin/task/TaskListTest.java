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

    @Test
    public void testRetrieveTask() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Complete homework");
        taskList.addTask(task);
        assertEquals(task, taskList.getTask(0)); // ✅ Ensures retrieving task works
    }

    @Test
    public void testMarkTaskAsDone() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Workout");
        taskList.addTask(task);
        taskList.getTask(0).markAsDone();
        assertTrue(taskList.getTask(0).isDone()); // ✅ Ensures marking as done works
    }

    @Test
    public void testUnmarkTask() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Read book");
        taskList.addTask(task);
        taskList.getTask(0).markAsDone();
        taskList.getTask(0).markAsNotDone();
        assertFalse(taskList.getTask(0).isDone()); // ✅ Ensures unmarking works
    }
}
