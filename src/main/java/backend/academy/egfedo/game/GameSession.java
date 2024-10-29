package backend.academy.egfedo.game;

import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.game.misc.LevelSupplier;
import backend.academy.egfedo.io.ResultOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class GameSession {

    private final GameMenu<WordRegistry.Category> categoryMenu;
    private final GameMenu<WordRegistry.Difficulty> difficultyMenu;
    private final LevelSupplier levelSupplier;
    private final ResultOutput resultOutput;

    public GameSession(GameMenu<WordRegistry.Category> categoryMenu,
        GameMenu<WordRegistry.Difficulty> difficultyMenu,
        LevelSupplier levelSupplier, ResultOutput resultOutput) {
        this.categoryMenu = Objects.requireNonNull(categoryMenu, "categoryMenu cannot be null");
        this.difficultyMenu = Objects.requireNonNull(difficultyMenu, "difficultyMenu cannot be null");
        this.levelSupplier = Objects.requireNonNull(levelSupplier, "levelSupplier cannot be null");
        this.resultOutput = Objects.requireNonNull(resultOutput, "resultOutput cannot be null");

    }

    public void run() {

        WordRegistry.Category category = categoryMenu.run();
        WordRegistry.Difficulty difficulty = difficultyMenu.run();

        var level = levelSupplier.getLevel(category, difficulty);

        boolean resultFlag = level.run();
        ResultOutput.Result result = resultFlag ? ResultOutput.Result.WIN : ResultOutput.Result.LOSE;
        String message = resultFlag ? "Хорошая работа!" : "Было загадано слово: " + level.word().toUpperCase();
        resultOutput.outputResult(result, message);

    }
}
