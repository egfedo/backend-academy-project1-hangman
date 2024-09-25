package backend.academy.egfedo.io.impl;

import backend.academy.egfedo.io.MenuOutput;
import java.io.PrintStream;
import java.util.List;

public final class MenuPrintStreamOutput implements MenuOutput {
    private final PrintStream output;

    public MenuPrintStreamOutput(PrintStream output) {
        this.output = output;
    }

    @Override
    public void displayMenu(String title, List<String> options) {
        output.println(title);
        output.println();
        int idx = 1;
        for (String option : options) {
            output.println(idx + ". " + option);
            output.println();
            idx++;
        }
        output.print("> ");
    }
}
