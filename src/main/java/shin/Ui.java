package shin;

import java.util.Scanner;
import shin.task.Task;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Shin");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showLoadingError() {
        System.out.println("Failed to load tasks. Starting fresh.");
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }


    public void closeScanner() {
        scanner.close();
    }
}
