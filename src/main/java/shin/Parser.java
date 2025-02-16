package shin;

public class Parser {
    public static String[] parse(String input) {
        String[] parts = input.trim().split(" ", 2);  // Split command and arguments
        String command = parts[0];

        // Validate arguments for specific commands
        switch (command) {
            case "schedule":
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Please specify a date in the format yyyy-MM-dd for the schedule command.");
                }
                break;

            // Add other commands requiring arguments here if needed
        }

        return parts; // Return the parsed command and arguments
    }
}
