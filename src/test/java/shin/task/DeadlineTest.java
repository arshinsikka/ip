package shin.task;

import org.junit.jupiter.api.Test;
import shin.exception.ShinException;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class DeadlineTest {

    @Test
    public void testDeadlineCreation() throws ShinException {
        Deadline deadline = new Deadline("Submit report", "2024-02-10");
        assertEquals("Submit report", deadline.getDescription());
        assertEquals(LocalDate.parse("2024-02-10"), deadline.getBy()); // ✅ Tests if date is stored correctly
    }

    @Test
    public void testToStringFormat() throws ShinException {
        Deadline deadline = new Deadline("Submit report", "2024-02-10");
        assertEquals("[D][ ] Submit report (by: Feb 10 2024)", deadline.toString()); // ✅ Ensures correct formatting
    }

    @Test
    public void testMarkAsDone() throws ShinException {
        Deadline deadline = new Deadline("Finish project", "2024-05-01");
        deadline.markAsDone();
        assertEquals("[D][X] Finish project (by: May 1 2024)", deadline.toString()); // ✅ Check task marked as done
    }

    @Test
    public void testInvalidDateFormat() {
        Exception exception = assertThrows(ShinException.class, () -> new Deadline("Invalid task", "10-02-2024"));
        assertEquals("Invalid date format! Use yyyy-MM-dd.", exception.getMessage()); // ✅ Ensures exception is thrown for wrong format
    }
}
