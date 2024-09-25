package backend.academy.egfedo.io.impl;

import backend.academy.egfedo.io.ResultOutput;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

public final class ResultPrintStreamOutput implements ResultOutput {

    private final PrintStream printStream;

    private final List<String> templates = List.of(
        """

                           __     ______  _    _   _      ____   _____ ______\s
                           \\ \\   / / __ \\| |  | | | |    / __ \\ / ____|  ____|
                            \\ \\_/ / |  | | |  | | | |   | |  | | (___ | |__  \s
                             \\   /| |  | | |  | | | |   | |  | |\\___ \\|  __| \s
                              | | | |__| | |__| | | |___| |__| |____) | |____\s
                              |_|  \\____/ \\____/  |______\\____/|_____/|______|

            """,
        """

                          __     ______  _    _   __          _______ _   _\s
                          \\ \\   / / __ \\| |  | |  \\ \\        / /_   _| \\ | |
                           \\ \\_/ / |  | | |  | |   \\ \\  /\\  / /  | | |  \\| |
                            \\   /| |  | | |  | |    \\ \\/  \\/ /   | | | . ` |
                             | | | |__| | |__| |     \\  /\\  /   _| |_| |\\  |
                             |_|  \\____/ \\____/       \\/  \\/   |_____|_| \\_|

            """
    );

    public ResultPrintStreamOutput(PrintStream printStream) {
        this.printStream = Objects.requireNonNull(printStream, "printStream cannot be null");
    }

    @Override
    public void outputResult(Result result, String message) {
        switch (result) {
            case WIN -> printStream.println(templates.get(1));
            case LOSE -> printStream.println(templates.getFirst());
            default -> throw new IllegalStateException("Unexpected result value: " + result);
        }

        printStream.println(message);
        printStream.println();
        printStream.println();
    }
}
