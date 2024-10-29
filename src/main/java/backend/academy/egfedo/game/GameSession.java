package backend.academy.egfedo.game;

import backend.academy.egfedo.data.Category;
import backend.academy.egfedo.data.Difficulty;
import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.game.misc.LevelSupplier;
import backend.academy.egfedo.io.ResultOutput;
import java.util.Objects;

public final class GameSession {

    private final GameMenu<Category> categoryMenu;
    private final GameMenu<Difficulty> difficultyMenu;
    private final LevelSupplier levelSupplier;
    private final ResultOutput resultOutput;

    public GameSession(GameMenu<Category> categoryMenu,
        GameMenu<Difficulty> difficultyMenu,
        LevelSupplier levelSupplier, ResultOutput resultOutput) {
        this.categoryMenu = Objects.requireNonNull(categoryMenu, "categoryMenu cannot be null");
        this.difficultyMenu = Objects.requireNonNull(difficultyMenu, "difficultyMenu cannot be null");
        this.levelSupplier = Objects.requireNonNull(levelSupplier, "levelSupplier cannot be null");
        this.resultOutput = Objects.requireNonNull(resultOutput, "resultOutput cannot be null");

    }

    public void run() {

        Category category = categoryMenu.run();
        Difficulty difficulty = difficultyMenu.run();

        var level = levelSupplier.getLevel(category, difficulty);

        boolean resultFlag = level.run();
        ResultOutput.Result result = resultFlag ? ResultOutput.Result.WIN : ResultOutput.Result.LOSE;
        String message = resultFlag ? "Хорошая работа!" : "Было загадано слово: " + level.word().toUpperCase();
        resultOutput.outputResult(result, message);

    }
}
