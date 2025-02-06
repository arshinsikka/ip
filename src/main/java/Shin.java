import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Shin {
    private static final String FILE_PATH = "data/duke.txt";

    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Shin");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage(FILE_PATH);
        ArrayList<Task> tasks = new ArrayList<>();

        // Load tasks from file
        try {
            tasks = storage.load();
        } catch (IOException e) {
            System.out.println("Failed to load tasks from file. Starting fresh.");
        }

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            try {
                if (command.equals("bye")) {
                    storage.save(tasks); // Save tasks before exiting
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;

                } else if (command.equals("list")) {
                    printTaskList(tasks);

                } else if (command.equals("event")) {
                    String[] eventDetails = parts[1].split("/from|/to");
                    Task newEvent = new Event(eventDetails[0].trim(), eventDetails[1].trim(), eventDetails[2].trim());
                    tasks.add(newEvent);
                    storage.save(tasks); // Save after adding a new event
                    printTaskAdded(newEvent, tasks.size());

                } else if (command.equals("deadline")) {
                    String[] deadlineDetails = parts[1].split("/by", 2);
                    Task newDeadline = new Deadline(deadlineDetails[0].trim(), deadlineDetails[1].trim());
                    tasks.add(newDeadline);
                    storage.save(tasks); // Save after adding a deadline
                    printTaskAdded(newDeadline, tasks.size());

                } else if (command.equals("todo")) {
                    Task newTask = new Todo(parts[1]);
                    tasks.add(newTask);
                    storage.save(tasks); // Save after adding a ToDo
                    printTaskAdded(newTask, tasks.size());

                } else if (command.equals("mark")) {
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    tasks.get(taskIndex).markAsDone();
                    storage.save(tasks); // Save after marking as done
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(taskIndex));
                    System.out.println("____________________________________________________________");

                } else if (command.equals("unmark")) {
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    tasks.get(taskIndex).markAsNotDone();
                    storage.save(tasks); // Save after unmarking
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(taskIndex));
                    System.out.println("____________________________________________________________");

                } else if (command.equals("delete")) {
                    int indexToDelete = Integer.parseInt(parts[1]) - 1;
                    Task removedTask = tasks.remove(indexToDelete);
                    storage.save(tasks); // Save after deleting
                    System.out.println("____________________________________________________________");
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");

                } else {
                    throw new ShinException("Oops! I'm sorry, but I don't know what that means :-(");
                }

            } catch (ShinException | IOException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
        scanner.close();
    }

    private static void printTaskList(ArrayList<Task> tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}