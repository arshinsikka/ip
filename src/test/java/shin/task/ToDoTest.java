package shin.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ToDoTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Read a book");
        assertEquals("Read a book", todo.getDescription()); // ✅ Checks if description is stored correctly
        assertFalse(todo.isDone()); // ✅ Ensures default task is not done
    }

    @Test
    public void testMarkAsDone() {
        Todo todo = new Todo("Finish assignment");
        todo.markAsDone();
        assertTrue(todo.isDone()); // ✅ Ensures marking as done works
    }

    @Test
    public void testToStringFormat() {
        Todo todo = new Todo("Go jogging");
        assertEquals("[T][ ] Go jogging", todo.toString()); // ✅ Ensures correct formatting
    }

    @Test
    public void testToStringAfterMarkingDone() {
        Todo todo = new Todo("Cook dinner");
        todo.markAsDone();
        assertEquals("[T][X] Cook dinner", todo.toString()); // ✅ Ensures formatting reflects completion
    }
}
