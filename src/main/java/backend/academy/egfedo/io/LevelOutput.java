package backend.academy.egfedo.io;

import java.util.Set;

public interface LevelOutput {
    void displayFrame(
        char[] word,
        String clue,
        Set<Character> usedLetters,
        int mistakes,
        int maxMistakes
    );
}
