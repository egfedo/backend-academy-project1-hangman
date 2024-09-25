package backend.academy.egfedo.io.impl;

import backend.academy.egfedo.io.GameInput;
import java.util.Objects;
import java.util.Scanner;

public final class ScannerGameInput implements GameInput {

    private final Scanner scanner;

    public ScannerGameInput(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "scanner cannot be null");
    }

    @Override
    public Command getCommand() {
        String input = scanner.nextLine();
        return new Command(
            input.length() == 1 ? Command.Type.CHAR : Command.Type.STRING,
            input
        );
    }
}
