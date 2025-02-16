package shin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate startDate;
    protected LocalDate endDate;

    public Event(String description, String from, String to) {
        super(description);
        assert from != null && to != null : "Event dates cannot be null";
        this.startDate = LocalDate.parse(from);
        this.endDate = LocalDate.parse(to);
        assert !this.startDate.isAfter(this.endDate) : "Event 'from' date cannot be after 'to' date";
    }


    public LocalDate getStartDate() {   // ✅ Add this getter
        return startDate;
    }

    public LocalDate getEndDate() {   // ✅ Add this getter
        return endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
