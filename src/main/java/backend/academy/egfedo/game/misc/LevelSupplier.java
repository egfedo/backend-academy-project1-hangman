package backend.academy.egfedo.game.misc;

import backend.academy.egfedo.data.Category;
import backend.academy.egfedo.data.Difficulty;
import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.game.level.GameLevel;
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
    public GameLevel getLevel(Category category, Difficulty difficulty) {


        var difficultyValues = Difficulty.values();
        Difficulty realDifficulty =
            difficulty == Difficulty.RANDOM
                ? difficultyValues[random.nextInt(difficultyValues.length) - 1]
                : difficulty;

        return new GameLevel(
            gameInput, levelOutput,
            registry.getRandomWord(category, realDifficulty),
            realDifficulty.maxErrors,
            registry.alphabet()
        );
    }
}
