package shin.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {   // ✅ Add this getter
        return description;
    }

    public boolean isDone() {   // ✅ Add this getter
        return isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // ✅ Returns "[X]" if done, "[ ]" if not
    }


    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
