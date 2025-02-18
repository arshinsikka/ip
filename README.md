# Shin - A Smart Task Manager 📝

Shin is a simple, interactive chatbot that helps users manage their tasks efficiently.
This project was developed as part of the CS2103T Individual Project.

---

## Features ✨
- **Task Management**: Add, mark, and delete tasks (ToDo, Deadline, Event).
- **GUI Support**: Interactive JavaFX-based graphical interface.
- **Persistence**: Tasks are stored and reloaded on startup.
- **Error Handling**: Gracefully handles invalid commands and missing files.

---

## Usage 🚀
Run the JAR file:
```sh
java -jar shin.jar
Commands:

todo <task> - Adds a ToDo task.
deadline <task> /by yyyy-MM-dd - Adds a Deadline task.
event <task> /from yyyy-MM-dd /to yyyy-MM-dd - Adds an Event task.
mark <task_number> - Marks task as completed.
unmark <task_number> - Unmarks task.
delete <task_number> - Deletes a task.
list - Displays all tasks.
bye - Exits the program.

AI Assistance 🤖
Some portions of this project were improved with AI assistance to enhance code quality and productivity:

Error Handling Improvements (Storage.java): AI suggested better exception handling and missing file creation methods.
Refactoring of Date Formatting (Deadline.java, Event.java): AI recommended more efficient date parsing methods.
JUnit Test Cases (DeadlineTest.java, TaskListTest.java): AI-assisted in generating additional test cases for edge cases.
All AI-assisted code was reviewed, modified, and adapted to ensure correctness and compliance with the course policy.

Acknowledgments 🎓
This project was developed as part of the CS2103T course at NUS. Special thanks to:
The CS2103T teaching team for guidance.
Online Java resources such as StackOverflow for debugging inspiration.
