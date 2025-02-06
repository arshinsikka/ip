public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;  // Added TaskType enum

    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    // Ensure these methods are present
    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + type.name().charAt(0) + "]" + "[" + getStatusIcon() + "] " + description;
    }
}
