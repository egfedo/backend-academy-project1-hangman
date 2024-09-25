package backend.academy;

import backend.academy.egfedo.config.io.LevelPrintStreamOutputConfig;
import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.game.GameSession;
import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.game.misc.LevelSupplier;
import backend.academy.egfedo.io.impl.LevelPrintStreamOutput;
import backend.academy.egfedo.io.impl.MenuPrintStreamOutput;
import backend.academy.egfedo.io.impl.ScannerGameInput;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    @SuppressWarnings({"MultipleStringLiterals"})
    public static void main(String[] args) {

        var config = new LevelPrintStreamOutputConfig("./config/output_config.yml").getData();
        var menuOutput = new MenuPrintStreamOutput(System.out);
        var levelOutput = new LevelPrintStreamOutput(System.out, config);

        var scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        var gameInput = new ScannerGameInput(scanner);

        var registry = new WordRegistry();
        var levelSupplier = new LevelSupplier(levelOutput, gameInput, registry);

        var catMenu = new GameMenu(
            "Выберите категорию:",
            GameMenu.OptionType.CATEGORY,
            List.of("Фрукты", "Города", "Случайно"),
            List.of("fruits", "cities", "random"),
            menuOutput,
            gameInput
        );

        var diffMenu = new GameMenu(
            "Choose difficulty:",
            GameMenu.OptionType.DIFFICULTY,
            List.of("Легко", "Средне", "Сложно", "Случайно"),
            List.of("easy", "medium", "hard", "random"),
            menuOutput,
            gameInput
        );

        var gameSession = new GameSession(
            List.of(catMenu, diffMenu),
            levelSupplier
        );

        gameSession.run();
    }
}
