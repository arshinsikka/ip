package shin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, String from, String to) {
        super(description);
        assert from != null && to != null : "Event dates cannot be null";
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
        assert !this.from.isAfter(this.to) : "Event 'from' date cannot be after 'to' date";
    }

    public LocalDate getFrom() {   // ✅ Add this getter
        return from;
    }

    public LocalDate getTo() {   // ✅ Add this getter
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
