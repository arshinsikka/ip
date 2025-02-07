import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
            Task task;

            switch (taskType) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    task = new Deadline(description, LocalDate.parse(taskDetails[3].trim()).toString());
                    break;
                case "E":
                    task = new Event(description, taskDetails[3].trim(), taskDetails[4].trim());
                    break;
                default:
                    continue;
            }
            if (isDone) {
                task.markAsDone();
            }
            tasks.add(task);
        }
        br.close();
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (Task task : tasks) {
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
                    + ((Deadline) task).by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (task instanceof Event) {
            return "E | " + status + " | " + task.description + " | " + ((Event) task).from + " | " + ((Event) task).to;
        }
        return "";
    }
}
