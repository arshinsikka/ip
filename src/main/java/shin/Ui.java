package shin;

import java.util.Scanner;
import shin.task.Task;

/**
 * Handles user interaction with the chatbot.
 * This class manages input and output operations for the chatbot.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a Ui object that initializes a scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a welcome message when the chatbot starts.
     */
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Shin");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    /**
     * Reads a command from the user.
     *
     * @return The user input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a horizontal line for formatting console output.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }


    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays an error message when loading tasks fails.
     */
    public void showLoadingError() {
        System.out.println("Failed to load tasks. Starting fresh.");
    }

    /**
     * Displays a confirmation message when a task is added.
     *
     * @param task      The task that was added.
     * @param taskCount The total number of tasks after adding the new task.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Closes the scanner to free up resources.
     */
    public void closeScanner() {
        scanner.close();
    }
}
