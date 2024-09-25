package backend.academy.egfedo.game;

import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.game.misc.LevelSupplier;
import backend.academy.egfedo.io.ResultOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class GameSession {

    private final List<GameMenu> menuList;
    private final LevelSupplier levelSupplier;
    private final ResultOutput resultOutput;

    public GameSession(List<GameMenu> menuList, LevelSupplier levelSupplier, ResultOutput resultOutput) {
        this.menuList = Objects.requireNonNull(menuList, "menuList cannot be null");
        this.levelSupplier = Objects.requireNonNull(levelSupplier, "levelSupplier cannot be null");
        this.resultOutput = Objects.requireNonNull(resultOutput, "resultOutput cannot be null");

        if (menuList.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("menuList cannot contain null values");
        }

    }

    public void run() {
        Map<GameMenu.OptionType, String> gameOptions = new HashMap<>();

        for (GameMenu menu : menuList) {
            menu.run(gameOptions);
        }

        var level = levelSupplier.getLevel(gameOptions);

        boolean resultFlag = level.run();
        ResultOutput.Result result = resultFlag ? ResultOutput.Result.WIN : ResultOutput.Result.LOSE;
        String message = resultFlag ? "Хорошая работа!" : "Было загадано слово: " + level.word().toUpperCase();
        resultOutput.outputResult(result, message);

    }
}
