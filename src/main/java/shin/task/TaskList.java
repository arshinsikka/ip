package shin.task;

import shin.task.Task;

import java.util.ArrayList;



/**
 * Manages a list of tasks.
 */

public class TaskList {
    private ArrayList<Task> tasks;

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
                .forEach(task -> System.out.println((tasks.indexOf(task) + 1) + ". " + task));
        System.out.println("____________________________________________________________");
    }

}
