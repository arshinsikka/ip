package shin.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description and type.
     *
     * @param description The task description.
     */

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

    /**
     * Gets the status icon of the task.
     *
     * @return "X" if the task is done, otherwise a space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // ✅ Returns "[X]" if done, "[ ]" if not
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }


    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
