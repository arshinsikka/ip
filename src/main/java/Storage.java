import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Storage {
    private String filePath;

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

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] taskDetails = line.split("\\|");
            String taskType = taskDetails[0].trim();
            boolean isDone = taskDetails[1].trim().equals("1");
            String description = taskDetails[2].trim();
            Task task = null;

            switch (taskType) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    try {
                        LocalDate parsedDate = LocalDate.parse(taskDetails[3].trim());
                        task = new Deadline(description, parsedDate.toString());
                    } catch (Exception e) {
                        System.out.println("Error parsing deadline date: " + taskDetails[3]);
                    }
                    break;
                case "E":
                    try {
                        LocalDate fromDate = LocalDate.parse(taskDetails[3].trim());
                        LocalDate toDate = LocalDate.parse(taskDetails[4].trim());
                        task = new Event(description, fromDate.toString(), toDate.toString());
                    } catch (Exception e) {
                        System.out.println("Error parsing event date: " + taskDetails[3] + " to " + taskDetails[4]);
                    }
                    break;
                default:
                    System.out.println("Unknown task type found: " + taskType);
                    continue;
            }

            if (task != null) {
                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        }
        br.close();
        return tasks;
    }

    // Save tasks to file
    public void save(TaskList taskList) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (Task task : taskList.getTasks()) {
            String taskData = taskToString(task);
            bw.write(taskData);
            bw.newLine();
        }
        bw.close();
    }

    private String taskToString(Task task) {
        String status = task.isDone ? "1" : "0";
        if (task instanceof Todo) {
            return "T | " + status + " | " + task.description;
        } else if (task instanceof Deadline) {
            return "D | " + status + " | " + task.description + " | "
                    + ((Deadline) task).by.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (task instanceof Event) {
            return "E | " + status + " | " + task.description + " | "
                    + ((Event) task).from.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    + " | " + ((Event) task).to.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return "";
    }
}
