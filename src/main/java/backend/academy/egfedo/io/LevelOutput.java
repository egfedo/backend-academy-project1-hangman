package backend.academy.egfedo.io;

import java.util.List;
import java.util.Set;

public interface LevelOutput {
    void displayFrame(
        List<Character> word,
        String clue,
        Set<Character> usedLetters,
        int mistakes,
        int maxMistakes
    );
}
