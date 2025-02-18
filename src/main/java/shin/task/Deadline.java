package shin.task;

import shin.exception.ShinException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 */

public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Constructs a Deadline task with a description and due date.
     *
     * @param description The task description.
     * @param by The due date in yyyy-MM-dd format.
     */

    public Deadline(String description, String by) throws ShinException {
        super(description);
        assert by != null : "Deadline date cannot be null";
        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new ShinException("Invalid date format! Use yyyy-MM-dd.");
        }
    }

    public LocalDate getBy() {   // ✅ Add this getter
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

}
