package backend.academy.egfedo.game;

import backend.academy.egfedo.config.io.LevelPrintStreamOutputConfig;
import backend.academy.egfedo.data.Category;
import backend.academy.egfedo.data.Difficulty;
import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.game.misc.LevelSupplier;
import backend.academy.egfedo.io.impl.LevelPrintStreamOutput;
import backend.academy.egfedo.io.impl.MenuPrintStreamOutput;
import backend.academy.egfedo.io.impl.ResultPrintStreamOutput;
import backend.academy.egfedo.io.impl.ScannerGameInput;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Game {

    public static void setUpSession() {
        var config = new LevelPrintStreamOutputConfig("./config/output_config.yml").getData();
        var menuOutput = new MenuPrintStreamOutput(System.out);
        var levelOutput = new LevelPrintStreamOutput(System.out, config);
        var resultOutput = new ResultPrintStreamOutput(System.out);

        var scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        var gameInput = new ScannerGameInput(scanner);

        var registry = new WordRegistry();
        var levelSupplier = new LevelSupplier(levelOutput, gameInput, registry);

        GameMenu<Category> catMenu = new GameMenu<>(
            "Выберите категорию:",
            menuOutput,
            gameInput,
            Category.class
        );

        GameMenu<Difficulty> diffMenu = new GameMenu<>(
            "Выберите сложность:",
            menuOutput,
            gameInput,
            Difficulty.class
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
