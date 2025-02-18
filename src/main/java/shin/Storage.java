package shin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import shin.task.Task;
import shin.task.Todo;
import shin.task.Deadline;
import shin.task.Event;
import shin.task.TaskList;
import shin.task.TaskType;
import shin.exception.ShinException;

public class Storage {
    private String filePath;
    private static final Logger logger = Logger.getLogger(Storage.class.getName());

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    // Load tasks from file

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // Ensure parent directory exists
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            Files.createFile(Paths.get(filePath));
            return tasks;
        }

        // Use try-with-resources to ensure BufferedReader is closed automatically
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] taskDetails = line.split("\\|");

                // Ensure correct format (at least 3 elements required)
                if (taskDetails.length < 3) {
                    logger.log(Level.WARNING, "Skipping malformed task entry: {0}", line);
                    continue;
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
                            try {
                                LocalDate parsedDate = LocalDate.parse(taskDetails[3].trim());
                                task = new Deadline(description, parsedDate.toString());
                            } catch (DateTimeParseException | ShinException e) {
                                logger.log(Level.WARNING, "Skipping invalid deadline entry: {0}", line);
                                continue; // ✅ Skip this line without crashing
                            }
                            break;

                        case "E":
                            if (taskDetails.length < 5) {
                                logger.log(Level.WARNING, "Invalid event format: {0}", line);
                                continue;
                            }
                            LocalDate fromDate = LocalDate.parse(taskDetails[3].trim());
                            LocalDate toDate = LocalDate.parse(taskDetails[4].trim());
                            task = new Event(description, fromDate.toString(), toDate.toString());
                            break;
                        default:
                            logger.log(Level.SEVERE, "Unknown task type found: {0}", taskType);
                            continue;
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        tasks.add(task);
                    }
                } catch (DateTimeParseException e) {
                    logger.log(Level.SEVERE, "Error parsing date for task: {0}", line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file: {0}", filePath);
            throw e;
        }
        return tasks;
    }

    // Save tasks to file
    public void save(TaskList taskList) throws IOException {
        assert taskList != null : "TaskList cannot be null";
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        taskList.getTasks()
                .stream()
                .map(this::taskToString)
                .forEach(taskData -> {
                    try {
                        bw.write(taskData);
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        bw.close();
    }


    private String taskToString(Task task) {
        String status = task.isDone() ? "1" : "0";  // ✅ Use getter for isDone

        if (task instanceof Todo) {
            return "T | " + status + " | " + task.getDescription();  // ✅ Use getter
        } else if (task instanceof Deadline) {
            return "D | " + status + " | " + task.getDescription() + " | " + ((Deadline) task).getBy().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));  // ✅ Use getter
        } else if (task instanceof Event) {
            return "E | " + status + " | " + task.getDescription() + " | " + ((Event) task).getFrom().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " | " + ((Event) task).getTo().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));  // ✅ Use getters
        }
        return "";
    }

}
