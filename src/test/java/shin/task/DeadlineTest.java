package shin.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class DeadlineTest {

    @Test
    public void testDeadlineCreation() {
        Deadline deadline = new Deadline("Submit report", "2024-02-10");
        assertEquals("Submit report", deadline.getDescription());
        assertEquals(LocalDate.parse("2024-02-10"), deadline.getBy()); // ✅ Tests if date is stored correctly
    }

    @Test
    public void testToStringFormat() {
        Deadline deadline = new Deadline("Submit report", "2024-02-10");
        assertEquals("[D][ ] Submit report (by: Feb 10 2024)", deadline.toString()); // ✅ Ensures correct formatting
    }
}
