package backend.academy.egfedo.game.menu;

import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.MenuOutput;
import java.util.Arrays;
import java.util.Objects;

public final class GameMenu<T extends Enum<T>> {

    private final String title;
    private final MenuOutput output;
    private final GameInput input;
    private final Class<T> enumClass;

    public GameMenu(String title,
        MenuOutput output, GameInput input, Class<T> enumClass) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.output = Objects.requireNonNull(output, "output cannot be null");
        this.input = Objects.requireNonNull(input, "input cannot be null");
        this.enumClass = Objects.requireNonNull(enumClass, "enumClass cannot be null");
    }

    public T run() {
        var optionStrings = Arrays.stream(this.enumClass.getEnumConstants())
            .map(Objects::toString).toList();
        output.displayMenu(
            this.title,
            optionStrings
        );

        while (true) {
            var cmd = input.getCommand();
            String data = cmd.data();
            try {
                int in = Integer.parseInt(data) - 1;
                if (in >= 0 && in < this.enumClass.getEnumConstants().length) {
                    return this.enumClass.getEnumConstants()[in];
                }
            } catch (NumberFormatException e) {

            }

            output.displayMenu(this.title, optionStrings);
        }
    }

}
