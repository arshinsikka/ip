Shin - A Smart Task Manager 📝
Shin is a simple, interactive chatbot that helps users manage their tasks efficiently.
This project was developed as part of the CS2103T Individual Project at National University of Singapore (NUS), CS2103T.

Features ✨
📝 Task Management: Add, mark, unmark, and delete tasks (ToDo, Deadline, Event).
🎨 GUI Support: Interactive JavaFX-based graphical interface.
💾 Persistent Storage: Tasks are saved and automatically reloaded on startup.
🚦 Robust Error Handling: Gracefully handles invalid commands and missing files.
⚡ AI-Assisted Improvements: Enhancements in code efficiency and test coverage.

Getting Started 🚀
Prerequisites

Ensure you have the following installed:
Java 17 (including JavaFX)
Git (for cloning the repository)

Installation & Running
Clone this repository:
git clone https://github.com/yourusername/ip.git
cd ip
Compile and run the application:
./gradlew run
OR run the JAR file:
java -jar shin.jar

Usage Commands 💻
Below are the commands Shin supports:

Task Commands:
todo <task> - Adds a ToDo task.
deadline <task> /by YYYY-MM-DD - Adds a Deadline task.
event <task> /from YYYY-MM-DD /to YYYY-MM-DD - Adds an Event.

Task Management:
list - Displays all tasks.
mark <task_number> - Marks a task as completed.
unmark <task_number> - Unmarks a completed task.
delete <task_number> - Deletes a task.

Other:
bye - Exits the program.

A preview of your chatbot GUI (this is where your Ui.png goes!).

![Shin Task Assistant](docs/Ui.png)

Code Structure 📂
Shin - Main chatbot logic and command handling.
TaskList - Manages task storage and operations.
Task, Todo, Deadline, Event - Different task types.
Storage - Handles saving and loading tasks from disk.
Ui - Manages user interaction and messages.
MainWindow - JavaFX UI Controller.

AI Assistance 🤖
Some portions of this project were improved with AI assistance to enhance code quality and productivity:
Error Handling Improvements (Storage.java): AI suggested better exception handling and missing file creation methods.
Refactoring of Date Formatting (Deadline.java, Event.java): AI recommended more efficient date parsing methods.
JUnit Test Cases (DeadlineTest.java, TaskListTest.java): AI-assisted in generating additional test cases for edge cases.

All AI-assisted code was reviewed, modified, and adapted to ensure correctness and compliance with the course policy.

Acknowledgments 🎓
This project was developed as part of the CS2103T Software Engineering module at NUS. Special thanks to:
The CS2103T teaching team for guidance.
Online Java resources such as StackOverflow for debugging inspiration.
SE-EDU GitHub repo for project structure references.

License 📜
This project is open-source under the MIT License.
