package shin;
import shin.Parser;
import shin.Ui;

import shin.task.Task;
import shin.task.Todo;
import shin.task.Deadline;
import shin.task.Event;
import shin.task.TaskList;
import shin.exception.ShinException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
/**
 * The main chatbot application that handles user input and task management.
 */
public class Shin {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes the chatbot with the given file path for storage.
     *
     * @param filePath The file path where tasks are stored.
     */

    public Shin(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public Shin() {
        // Initialize necessary fields if any
    }

    public String getResponse(String input) {
        // Return a response, you can customize this logic as needed
        return "Shin heard: " + input;
    }


    /**
     * Runs the chatbot, processing user commands in a loop until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                String[] parts = Parser.parse(fullCommand);  // Get command & arguments
                String command = parts[0];

                switch (command) {
                    case "bye":
                        isExit = true;
                        System.out.println("Bye. Hope to see you again soon!");
                        break;

                    case "list":
                        tasks.printTasks();
                        break;

                    case "todo":
                        if (parts.length < 2) {
                            ui.showError("Description for todo cannot be empty!");
                            break;
                        }
                        Task newTodo = new Todo(parts[1]);
                        tasks.addTask(newTodo);
                        storage.save(tasks);
                        ui.showTaskAdded(newTodo, tasks.size());
                        break;

                    case "deadline":
                        if (parts.length < 2 || !parts[1].contains(" /by ")) {
                            ui.showError("Invalid format! Use: deadline <desc> /by yyyy-MM-dd");
                            break;
                        }
                        String[] deadlineParts = parts[1].split(" /by ");
                        Task newDeadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                        tasks.addTask(newDeadline);
                        storage.save(tasks);
                        ui.showTaskAdded(newDeadline, tasks.size());
                        break;

                    case "schedule":
                        if (parts.length < 2) {
                            ui.showError("Please specify a date in the format yyyy-MM-dd.");
                            break;
                        }
                        try {
                            LocalDate date = LocalDate.parse(parts[1]); // Convert user input into LocalDate
                            tasks.viewSchedule(date); // Calls TaskList to filter and display tasks
                        } catch (DateTimeParseException e) {
                            ui.showError("Invalid date format! Please use: yyyy-MM-dd");
                        }
                        break;



                    case "event":
                        if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to ")) {
                            ui.showError("Invalid format! Use: event <desc> /from yyyy-MM-dd /to yyyy-MM-dd");
                            break;
                        }
                        String[] eventParts = parts[1].split(" /from | /to ");
                        Task newEvent = new Event(eventParts[0], eventParts[1], eventParts[2]);
                        tasks.addTask(newEvent);
                        storage.save(tasks);
                        ui.showTaskAdded(newEvent, tasks.size());
                        break;

                    case "mark":
                        try {
                            int index = Integer.parseInt(parts[1]) - 1;
                            tasks.getTask(index).markAsDone();
                            storage.save(tasks);
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println("  " + tasks.getTask(index));
                        } catch (Exception e) {
                            ui.showError("Invalid mark command! Use: mark <task number>");
                        }
                        break;

                    case "unmark":
                        try {
                            int index = Integer.parseInt(parts[1]) - 1;
                            tasks.getTask(index).markAsNotDone();
                            storage.save(tasks);
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println("  " + tasks.getTask(index));
                        } catch (Exception e) {
                            ui.showError("Invalid unmark command! Use: unmark <task number>");
                        }
                        break;

                    case "delete":
                        try {
                            int indexToDelete = Integer.parseInt(parts[1]) - 1;
                            Task removedTask = tasks.getTask(indexToDelete);
                            tasks.removeTask(indexToDelete);
                            storage.save(tasks);
                            System.out.println("Noted. I've removed this task:");
                            System.out.println("  " + removedTask);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        } catch (Exception e) {
                            ui.showError("Invalid delete command! Use: delete <task number>");
                        }
                        break;

                    default:
                        ui.showError("Unknown command.");
                }
                // Other cases...

                ui.showLine();
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
        ui.closeScanner();
    }

    /**
     * The main entry point of the chatbot application.
     *
     * @param args Command-line arguments (not used).
     */

    public static void main(String[] args) {
        new Shin("data/duke.txt").run();
    }
}
