package shin.task;

import shin.exception.ShinException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
     * @throws ShinException If the date format is incorrect.
     */
    public Deadline(String description, String by) throws ShinException {
        super(description);
        assert by != null : "Deadline date cannot be null";
        try {
            this.dueDate = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new ShinException("Invalid date format! Use yyyy-MM-dd.");
        }
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Formats the deadline date into a human-readable format.
     *
     * @@author arshinsikka-reused
     * AI-assisted refactoring: ChatGPT suggested using a utility method for formatting dates.
     */
    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
    // @@author

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatDate(dueDate) + ")";
    }
}
