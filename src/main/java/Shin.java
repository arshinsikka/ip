import java.util.Scanner;
import java.util.ArrayList;

public class Shin {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Shin");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();  // Use ArrayList instead of array

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            try {
                if (command.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;

                } else if (command.equals("list")) {
                    printTaskList(tasks);

                }

                else if (command.equals("event")) {
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new ShinException("Oops! The description of an event cannot be empty.");
                    }
                    String[] eventDetails = parts[1].split("/from|/to");
                    if (eventDetails.length < 3) {
                        throw new ShinException("Oops! The event must have a start and end time.");
                    }
                    Task newEvent = new Event(eventDetails[0].trim(), eventDetails[1].trim(), eventDetails[2].trim());
                    tasks.add(newEvent);
                    printTaskAdded(newEvent, tasks.size());

                }

                else if (command.equals("deadline")) {
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new ShinException("Oops! The description of a deadline cannot be empty.");
                    }
                    String[] deadlineDetails = parts[1].split("/by", 2);
                    if (deadlineDetails.length < 2) {
                        throw new ShinException("Oops! The deadline must have a due date.");
                    }
                    Task newDeadline = new Deadline(deadlineDetails[0].trim(), deadlineDetails[1].trim());
                    tasks.add(newDeadline);
                    printTaskAdded(newDeadline, tasks.size());
                }
                else if (command.equals("todo")) {
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new ShinException("Oops! The description of a todo cannot be empty.");
                    }
                    Task newTask = new Todo(parts[1]);
                    tasks.add(newTask);
                    printTaskAdded(newTask, tasks.size());

                }

                else if (command.equals("mark")) {
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new ShinException("Oops! The task number you entered doesn't exist.");
                    }
                    tasks.get(taskIndex).markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(taskIndex));
                    System.out.println("____________________________________________________________");

                } else if (command.equals("unmark")) {
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new ShinException("Oops! The task number you entered doesn't exist.");
                    }
                    tasks.get(taskIndex).markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(taskIndex));
                    System.out.println("____________________________________________________________");
                } else if (command.equals("delete")) {
                    int indexToDelete = Integer.parseInt(parts[1]) - 1;
                    if (indexToDelete < 0 || indexToDelete >= tasks.size()) {
                        throw new ShinException("Oops! The task number you entered doesn't exist.");
                    }
                    Task removedTask = tasks.remove(indexToDelete);
                    System.out.println("____________________________________________________________");
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");

                } else {
                    throw new ShinException("Oops! I'm sorry, but I don't know what that means :-(");
                }

            } catch (ShinException e) {
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
