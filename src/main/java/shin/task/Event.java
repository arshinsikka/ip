package shin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate StartDate;
    protected LocalDate EndDate;

    public Event(String description, String from, String to) {
        super(description);
        assert from != null && to != null : "Event dates cannot be null";
        this.StartDate = LocalDate.parse(from);
        this.EndDate = LocalDate.parse(to);
        assert !this.StartDate.isAfter(this.EndDate) : "Event 'from' date cannot be after 'to' date";
    }

    public LocalDate getStartDate() {   // ✅ Add this getter
        return StartDate;
    }

    public LocalDate getEndDate() {   // ✅ Add this getter
        return EndDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + StartDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + EndDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
