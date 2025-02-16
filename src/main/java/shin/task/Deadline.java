package shin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents a task with a deadline.
 */

public class Deadline extends Task {
    protected LocalDate dueDate;

    /**
     * Constructs a Deadline task with a description and due date.
     *
     * @param description The task description.
     * @param by The due date in yyyy-MM-dd format.
     */

    public Deadline(String description, String by) {
        super(description);
        assert by != null : "Deadline date cannot be null";
        this.dueDate = LocalDate.parse(by);
    }

    public LocalDate getDueDate() {   // ✅ Add this getter
        return dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

}
