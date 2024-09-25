package backend.academy.egfedo.game.menu;

import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.MenuOutput;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class GameMenu {

    private final String title;
    private final OptionType optionType;
    private final List<String> options;
    private final List<String> optionData;
    private final MenuOutput output;
    private final GameInput input;

    public GameMenu(String title, OptionType optionType,
        List<String> options, List<String> optionData,
        MenuOutput output, GameInput input) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.optionType = Objects.requireNonNull(optionType, "optionType cannot be null");
        this.options = Objects.requireNonNull(options, "options cannot be null");
        this.optionData = Objects.requireNonNull(optionData, "optionData cannot be null");
        this.output = Objects.requireNonNull(output, "output cannot be null");
        this.input = Objects.requireNonNull(input, "input cannot be null");

        if (options.isEmpty()) {
            throw new IllegalArgumentException("options cannot be empty");
        }
        if (optionData.isEmpty()) {
            throw new IllegalArgumentException("optionData cannot be empty");
        }
        if (options.size() != optionData.size()) {
            throw new IllegalArgumentException("options and optionData lengths don't match");
        }
    }

    public enum OptionType {
        CATEGORY, DIFFICULTY
    }

    public void run(Map<OptionType, String> options) {
        output.displayMenu(this.title, this.options);

        while (true) {
            var cmd = input.getCommand();
            String data = cmd.data();
            try {
                int in = Integer.parseInt(data) - 1;
                if (in >= 0 && in < this.options.size()) {
                    options.put(this.optionType, this.optionData.get(in));
                    return;
                }
            } catch (NumberFormatException e) {

            }

            output.displayMenu(this.title, this.options);
        }
    }

}
