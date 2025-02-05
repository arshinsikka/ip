import java.util.Scanner;

public class Shin {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Shin");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            if (command.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;

            } else if (command.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");

            } else if (command.equals("mark")) {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
                System.out.println("____________________________________________________________");

            } else if (command.equals("unmark")) {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
                System.out.println("____________________________________________________________");

            } else if (command.equals("todo")) {
                Task newTask = new Todo(parts[1]);
                tasks[taskCount++] = newTask;
                printTaskAdded(newTask, taskCount);

            } else if (command.equals("deadline")) {
                String[] details = parts[1].split("/by", 2);
                Task newTask = new Deadline(details[0].trim(), details[1].trim());
                tasks[taskCount++] = newTask;
                printTaskAdded(newTask, taskCount);

            } else if (command.equals("event")) {
                String[] details = parts[1].split("/from|/to", 3);
                Task newTask = new Event(details[0].trim(), details[1].trim(), details[2].trim());
                tasks[taskCount++] = newTask;
                printTaskAdded(newTask, taskCount);

            } else {
                Task newTask = new Task(input);
                tasks[taskCount++] = newTask;
                printTaskAdded(newTask, taskCount);
            }
        }

        scanner.close();
    }

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}
todo