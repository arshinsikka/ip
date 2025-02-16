package shin;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import shin.task.*;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    // Load tasks from file
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
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
        }
        return tasks;
    }

    // Parse a task from a file line
    private Task parseTask(String line) {
        String[] taskDetails = line.split("\\|");
        String taskType = taskDetails[0].trim();
        boolean isDone = taskDetails[1].trim().equals("1");
        String description = taskDetails[2].trim();

        switch (taskType) {
            case "T":
                return new Todo(description);
            case "D":
                LocalDate dueDate = LocalDate.parse(taskDetails[3].trim());
                return new Deadline(description, dueDate.toString());
            case "E":
                LocalDate startDate = LocalDate.parse(taskDetails[3].trim());
                LocalDate endDate = LocalDate.parse(taskDetails[4].trim());
                return new Event(description, startDate.toString(), endDate.toString());
            default:
                return null;
        }
    }

    // Save tasks to file
    public void save(TaskList taskList) throws IOException {
        assert taskList != null : "TaskList cannot be null";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
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
        }
    }

    private String taskToString(Task task) {
        String status = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + status + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            return "D | " + status + " | " + task.getDescription() + " | "
                    + ((Deadline) task).getDueDate(); // Fixed to use getDueDate()
        } else if (task instanceof Event) {
            return "E | " + status + " | " + task.getDescription() + " | "
                    + ((Event) task).getStartDate() + " | " + ((Event) task).getEndDate();
        }
        return "";
    }
}
