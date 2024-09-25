package backend.academy.egfedo.game;

import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.game.misc.LevelSupplier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class GameSession {

    private final List<GameMenu> menuList;
    private final LevelSupplier levelSupplier;

    public GameSession(List<GameMenu> menuList, LevelSupplier levelSupplier) {
        this.menuList = Objects.requireNonNull(menuList, "menuList cannot be null");
        this.levelSupplier = Objects.requireNonNull(levelSupplier, "levelSupplier cannot be null");

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
        level.run();
    }
}
