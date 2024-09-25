package backend.academy.egfedo.game.misc;

import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.game.level.GameLevel;
import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.LevelOutput;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Objects;

public final class LevelSupplier {

    private final LevelOutput levelOutput;
    private final GameInput gameInput;
    private final WordRegistry registry;

    private final SecureRandom random = new SecureRandom();

    @SuppressWarnings({"MultipleStringLiterals"})
    private final Map<String, Integer> difficultyMappings = Map.of(
        "easy", 11,
        "medium", 7,
        "hard", 4
    );

    public LevelSupplier(
        LevelOutput levelOutput, GameInput gameInput,
        WordRegistry registry
    ) {
        this.levelOutput = Objects.requireNonNull(levelOutput, "Level output must not be null");
        this.gameInput = Objects.requireNonNull(gameInput, "Game input must not be null");
        this.registry = Objects.requireNonNull(registry, "Registry must not be null");
    }

    @SuppressWarnings({"MultipleStringLiterals"})
    public GameLevel getLevel(Map<GameMenu.OptionType, String> options) {
        Objects.requireNonNull(options, "Options must not be null");

        if (!options.containsKey(GameMenu.OptionType.CATEGORY)) {
            throw new IllegalArgumentException("Options must contain a category entry");
        }

        if (!options.containsKey(GameMenu.OptionType.DIFFICULTY)) {
            throw new IllegalArgumentException("Options must contain a difficulty entry");
        }

        var categoryStr = options.get(GameMenu.OptionType.CATEGORY);
        WordRegistry.Category category = switch (categoryStr) {
            case "fruits" -> WordRegistry.Category.FRUIT;
            case "cities" -> WordRegistry.Category.CITIES;
            case "random" -> null;
            default -> throw new IllegalArgumentException("Unexpected value for category: '" + categoryStr + "'");
        };

        var difficultyStr = options.get(GameMenu.OptionType.DIFFICULTY);
        WordRegistry.Difficulty difficulty = switch (difficultyStr) {
            case "easy" -> WordRegistry.Difficulty.EASY;
            case "medium" -> WordRegistry.Difficulty.MEDIUM;
            case "hard" -> WordRegistry.Difficulty.HARD;
            case "random" -> null;
            default -> throw new IllegalArgumentException("Unexpected value for difficulty: '" + difficultyStr + "'");
        };

        int tries;
        if ("random".equals(difficultyStr)) {
            var values = difficultyMappings.values().stream().toList();
            tries = values.get(random.nextInt(values.size()));
        } else {
            tries = difficultyMappings.get(difficultyStr);
        }

        return new GameLevel(
            gameInput, levelOutput,
            registry.getRandomWord(category, difficulty),
            tries,
            registry.alphabet()
        );
    }
}
