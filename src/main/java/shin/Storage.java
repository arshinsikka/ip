package shin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import shin.task.*;
import shin.exception.ShinException;

/**
 * Handles the loading and saving of tasks to a file.
 */
public class Storage {
    private String filePath;
    private static final Logger logger = Logger.getLogger(Storage.class.getName());

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path of the file where tasks will be stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the specified file.
     *
     * @return A list of tasks read from the file.
     * @throws IOException If an error occurs while reading the file.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            createNewFile(file);
            return tasks;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file: {0}", filePath);
            throw e;
        }
        return tasks;
    }

    /**
     * Parses a task from a line in the storage file.
     *
     * @param line The line to parse.
     * @return The parsed Task, or null if the format is incorrect.
     */
    private Task parseTask(String line) {
        String[] taskDetails = line.split("\\|");

        if (taskDetails.length < 3) {
            logger.log(Level.WARNING, "Skipping malformed task entry: {0}", line);
            return null;
        }

        String taskType = taskDetails[0].trim();
        boolean isDone = taskDetails[1].trim().equals("1");
        String description = taskDetails[2].trim();
        Task task = null;

        try {
            switch (taskType) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    task = parseDeadline(taskDetails, line);
                    break;
                case "E":
                    task = parseEvent(taskDetails, line);
                    break;
                default:
                    logger.log(Level.SEVERE, "Unknown task type found: {0}", taskType);
                    return null;
            }

            if (task != null && isDone) {
                task.markAsDone();
            }
        } catch (DateTimeParseException e) {
            logger.log(Level.SEVERE, "Error parsing task: {0}", line);
            return null;
        }
        return task;
    }

    /**
     * Parses a Deadline task from task details.
     *
     * @param taskDetails The task details array.
     * @param line        The original task line.
     * @return A Deadline task or null if invalid.
     */
    private Task parseDeadline(String[] taskDetails, String line) {
        try {
            LocalDate parsedDate = LocalDate.parse(taskDetails[3].trim());
            return new Deadline(taskDetails[2].trim(), parsedDate.toString());
        } catch (DateTimeParseException | ShinException e) {
            logger.log(Level.WARNING, "Skipping invalid deadline entry: {0}", line);
            return null;
        }
    }

    /**
     * Parses an Event task from task details.
     *
     * @param taskDetails The task details array.
     * @param line        The original task line.
     * @return An Event task or null if invalid.
     */
    private Task parseEvent(String[] taskDetails, String line) {
        if (taskDetails.length < 5) {
            logger.log(Level.WARNING, "Invalid event format: {0}", line);
            return null;
        }
        try {
            LocalDate fromDate = LocalDate.parse(taskDetails[3].trim());
            LocalDate toDate = LocalDate.parse(taskDetails[4].trim());
            return new Event(taskDetails[2].trim(), fromDate.toString(), toDate.toString());
        } catch (DateTimeParseException e) {
            logger.log(Level.SEVERE, "Error parsing event dates for task: {0}", line);
            return null;
        }
    }

    /**
     * Saves the current task list to the file.
     *
     * @param taskList The task list to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void save(TaskList taskList) throws IOException {
        assert taskList != null : "TaskList cannot be null";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : taskList.getTasks()) {
                bw.write(taskToString(task));
                bw.newLine();
            }
        }
    }

    /**
     * Converts a task into a formatted string for storage.
     *
     * @param task The task to be converted.
     * @return A string representation of the task.
     */
    private String taskToString(Task task) {
        String status = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + status + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            return "D | " + status + " | " + task.getDescription() + " | " +
                    ((Deadline) task).getBy().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (task instanceof Event) {
            return "E | " + status + " | " + task.getDescription() + " | " +
                    ((Event) task).getFrom().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                    " | " + ((Event) task).getTo().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return "";
    }

    /**
     * Creates a new file if it does not exist.
     *
     * @param file The file to create.
     * @throws IOException If an error occurs while creating the file.
     */
    private void createNewFile(File file) throws IOException {
        file.getParentFile().mkdirs();
        Files.createFile(Paths.get(filePath));
    }
}
