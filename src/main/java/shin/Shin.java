package shin;

import shin.task.Task;
import shin.task.Todo;
import shin.task.Deadline;
import shin.task.Event;
import shin.task.TaskList;
import shin.exception.ShinException;

import java.io.IOException;
import java.util.ArrayList;

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
        this.storage = new Storage(filePath);
        try {
            ArrayList<Task> loadedTasks = storage.load();
            tasks = (loadedTasks != null && !loadedTasks.isEmpty())
                    ? new TaskList(loadedTasks)
                    : new TaskList();
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Default constructor using the default storage path.
     */
    public Shin() {
        this("data/tasks.txt");
    }

    /**
     * Processes user input and returns an appropriate response from Shin.
     *
     * @param input The user's command or query.
     * @return A response string based on the user's input.
     */
    public String getResponse(String input) {
        if (tasks == null) {
            tasks = new TaskList();
        }
        String lower = input.toLowerCase().trim();
        if (lower.startsWith("todo"))      return handleTodo(input);
        if (lower.startsWith("deadline"))  return handleDeadline(input);
        if (lower.startsWith("event"))     return handleEvent(input);
        if (lower.equals("list"))          return handleList();
        if (lower.startsWith("mark"))      return handleMark(input);
        if (lower.startsWith("unmark"))    return handleUnmark(input);
        if (lower.startsWith("delete"))    return handleDelete(input);
        return handleMisc(lower);
    }

    // --- Helper methods for getResponse() ---

    private String handleTodo(String input) {
        String desc = input.substring(4).trim();
        if (desc.isEmpty()) return "Error: Todo description cannot be empty.";
        Task t = new Todo(desc);
        tasks.addTask(t);
        saveTasks();
        return "Got it! I've added that as a task.";
    }

    private String handleDeadline(String input) {
        if (!input.contains(" /by "))
            return "Error: Invalid deadline format. Use: deadline <desc> /by yyyy-MM-dd";
        String[] parts = input.substring(8).split(" /by ", 2);
        if (parts.length < 2) return "Error: Missing deadline description/date.";
        try {
            Task t = new Deadline(parts[0].trim(), parts[1].trim());
            tasks.addTask(t);
            saveTasks();
            return "Alright! I’ve set a deadline for this task.";
        } catch (ShinException e) {
            return "Error: " + e.getMessage();
        }
    }

    private String handleEvent(String input) {
        if (!input.contains(" /from ") || !input.contains(" /to "))
            return "Error: Invalid event format. Use: event <desc> /from yyyy-MM-dd /to yyyy-MM-dd";
        String[] parts = input.substring(5).split(" /from | /to ", 3);
        if (parts.length < 3) return "Error: Missing event description or date range.";
        Task t = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.addTask(t);
        saveTasks();
        return "Event added! Don't forget to mark it done when it's over.";
    }

    private String handleList() {
        if (tasks.size() == 0) return "Your task list is empty!";
        String output = tasks.getTaskListAsString();
        System.out.println("DEBUG: " + output);
        return output;
    }

    private String handleMark(String input) {
        try {
            int index = Integer.parseInt(input.substring(4).trim()) - 1;
            if (index < 0 || index >= tasks.size())
                return "Error: Task number out of range.";
            tasks.getTask(index).markAsDone();
            saveTasks();
            return "Nice! I've marked this task as done ✅.";
        } catch (NumberFormatException e) {
            return "Error: Invalid mark format. Use: mark <task number>";
        }
    }

    private String handleUnmark(String input) {
        try {
            int index = Integer.parseInt(input.substring(6).trim()) - 1;
            if (index < 0 || index >= tasks.size())
                return "Error: Task number out of range.";
            tasks.getTask(index).markAsNotDone();
            saveTasks();
            return "Alright, I've marked this task as not done yet.";
        } catch (NumberFormatException e) {
            return "Error: Invalid unmark format. Use: unmark <task number>";
        }
    }

    private String handleDelete(String input) {
        try {
            int index = Integer.parseInt(input.substring(6).trim()) - 1;
            if (index < 0 || index >= tasks.size())
                return "Error: Task number out of range.";
            tasks.removeTask(index);
            saveTasks();
            return "Got it, this task has been removed!";
        } catch (NumberFormatException e) {
            return "Error: Invalid delete format. Use: delete <task number>";
        }
    }

    private String handleMisc(String input) {
        if (input.contains("how are you"))
            return "I'm just a chatbot, but I'm here to help! 😊";
        if (input.contains("hello") || input.contains("hi"))
            return "Hey there! How can I assist you today?";
        if (input.contains("bye"))
            return "Goodbye! Hope to chat with you again soon. 👋";
        if (input.contains("help"))
            return "Sure! You can use commands like 'todo <task>', 'deadline <task> /by <date>', " +
                    "'event <task> /from <date> /to <date>', 'list', 'mark <task number>', " +
                    "'unmark <task number>', or 'delete <task number>'.";
        return "Hmm, I'm not sure what you mean. Try using 'help' to see what I can do! 🤔";
    }

    /**
     * Saves the current task list.
     */
    private void saveTasks() {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
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
                String[] parts = Parser.parse(fullCommand);
                isExit = executeCommand(parts);
                ui.showLine();
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
        ui.closeScanner();
    }

    /**
     * Executes the parsed command and returns true if the chatbot should exit.
     *
     * @param parts The parsed command and its arguments.
     * @return true if the command is "bye", false otherwise.
     */
    private boolean executeCommand(String[] parts) {
        String command = parts[0];
        if ("bye".equals(command)) return handleBye();
        if ("list".equals(command)) { tasks.printTasks(); return false; }
        if ("todo".equals(command)) { handleTodoCommand(parts); return false; }
        if ("deadline".equals(command)) { handleDeadlineCommand(parts); return false; }
        if ("event".equals(command)) { handleEventCommand(parts); return false; }
        if ("mark".equals(command)) { handleMarkCommand(parts); return false; }
        if ("unmark".equals(command)) { handleUnmarkCommand(parts); return false; }
        if ("delete".equals(command)) { handleDeleteCommand(parts); return false; }
        ui.showError("Unknown command.");
        return false;
    }

    // --- Command Handler Helpers for executeCommand() ---

    private boolean handleBye() {
        System.out.println("Bye. Hope to see you again soon!");
        return true;
    }

    private void handleTodoCommand(String[] parts) {
        if (parts.length < 2) {
            ui.showError("Description for todo cannot be empty!");
            return;
        }
        Task newTodo = new Todo(parts[1]);
        tasks.addTask(newTodo);
        saveTasks();
        ui.showTaskAdded(newTodo, tasks.size());
    }

    private void handleDeadlineCommand(String[] parts) {
        if (parts.length < 2 || !parts[1].contains(" /by ")) {
            ui.showError("Invalid format! Use: deadline <desc> /by yyyy-MM-dd");
            return;
        }
        String[] deadlineParts = parts[1].split(" /by ");
        try {
            Task newDeadline = new Deadline(deadlineParts[0], deadlineParts[1]);
            tasks.addTask(newDeadline);
            saveTasks();
            ui.showTaskAdded(newDeadline, tasks.size());
        } catch (ShinException e) {
            ui.showError(e.getMessage());
        }
    }

    private void handleEventCommand(String[] parts) {
        if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to ")) {
            ui.showError("Invalid format! Use: event <desc> /from yyyy-MM-dd /to yyyy-MM-dd");
            return;
        }
        String[] eventParts = parts[1].split(" /from | /to ");
        Task newEvent = new Event(eventParts[0], eventParts[1], eventParts[2]);
        tasks.addTask(newEvent);
        saveTasks();
        ui.showTaskAdded(newEvent, tasks.size());
    }

    private void handleMarkCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            tasks.getTask(index).markAsDone();
            saveTasks();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks.getTask(index));
        } catch (Exception e) {
            ui.showError("Invalid mark command! Use: mark <task number>");
        }
    }

    private void handleUnmarkCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            tasks.getTask(index).markAsNotDone();
            saveTasks();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks.getTask(index));
        } catch (Exception e) {
            ui.showError("Invalid unmark command! Use: unmark <task number>");
        }
    }

    private void handleDeleteCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            Task removedTask = tasks.getTask(index);
            tasks.removeTask(index);
            saveTasks();
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + removedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (Exception e) {
            ui.showError("Invalid delete command! Use: delete <task number>");
        }
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
