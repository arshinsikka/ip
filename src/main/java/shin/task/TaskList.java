package shin.task;

import java.util.ArrayList;

/**
 * Manages a list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with pre-loaded tasks.
     *
     * @param tasks The list of tasks to initialize with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        assert task != null : "Task cannot be null";
        tasks.add(task);
    }

    /**
     * Removes a task from the task list.
     *
     * @param index The index of the task to remove.
     */
    public void removeTask(int index) {
        tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The task list size.
     */
    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Prints all tasks in the list.
     */
    public void printTasks() {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        tasks.stream()
                .map(task -> (tasks.indexOf(task) + 1) + ". " + task) // Stream approach with task indexing
                .forEach(System.out::println);
        System.out.println("____________________________________________________________");
    }

    /**
     * Returns all tasks in the list as a formatted string.
     *
     * @return A string representation of all tasks.
     */
    public String getTaskListAsString() {
        if (tasks.isEmpty()) {
            return "Your task list is empty!";
        }

        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
