package backend.academy;

import backend.academy.egfedo.config.io.LevelPrintStreamOutputConfig;
import backend.academy.egfedo.data.Word;
import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.game.GameSession;
import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.game.misc.LevelSupplier;
import backend.academy.egfedo.io.impl.LevelPrintStreamOutput;
import backend.academy.egfedo.io.impl.MenuPrintStreamOutput;
import backend.academy.egfedo.io.impl.ResultPrintStreamOutput;
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
        var resultOutput = new ResultPrintStreamOutput(System.out);

        var scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        var gameInput = new ScannerGameInput(scanner);

        var registry = new WordRegistry();
        var levelSupplier = new LevelSupplier(levelOutput, gameInput, registry);

        GameMenu<WordRegistry.Category> catMenu = new GameMenu<>(
            "Выберите категорию:",
            menuOutput,
            gameInput,
            WordRegistry.Category.class
        );

        GameMenu<WordRegistry.Difficulty> diffMenu = new GameMenu<>(
            "Выберите сложность:",
            menuOutput,
            gameInput,
            WordRegistry.Difficulty.class
        );

        var gameSession = new GameSession(
            catMenu,
            diffMenu,
            levelSupplier,
            resultOutput
        );

        gameSession.run();
    }
}
