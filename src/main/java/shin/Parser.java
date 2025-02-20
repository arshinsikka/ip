package shin;

import shin.exception.ShinException;

public class Parser {
    public static String[] parse(String input) throws ShinException {
        if (input.isBlank()) {
            throw new ShinException("Command cannot be empty!");
        }
        return input.trim().split(" ", 2);
    }

}
