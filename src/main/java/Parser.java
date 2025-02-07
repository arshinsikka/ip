public class Parser {
    public static String[] parse(String input) {
        return input.trim().split(" ", 2);  // Splits command and arguments
    }
}
