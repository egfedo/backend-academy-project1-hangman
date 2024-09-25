package backend.academy.egfedo.game.level;

import backend.academy.egfedo.data.Word;
import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.LevelOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameLevel {

    private final GameInput input;
    private final LevelOutput output;

    private final Map<Character, List<Integer>> unsolvedMap = new HashMap<>();
    private final Set<Character> usedLetters = new HashSet<>();
    private final List<Character> wordDisplay = new ArrayList<>();

    private final Set<Character> alphabet;

    private final String clue;
    private boolean showClue = false;

    private int mistakes = 0;
    private final int maxMistakes;

    public GameLevel(GameInput input, LevelOutput output, Word word, int maxMistakes, Set<Character> alphabet) {
        this.input = input;
        this.output = output;
        this.clue = word.clue();
        this.maxMistakes = maxMistakes;
        this.alphabet = Set.copyOf(alphabet);

        for (int i = 0; i < word.text().length(); i++) {
            wordDisplay.add('_');
        }

        int idx = 0;
        for (var letter : word.text().toCharArray()) {
            if (!this.unsolvedMap.containsKey(letter)) {
                this.unsolvedMap.put(letter, new ArrayList<>());
            }
            this.unsolvedMap.get(letter).add(idx);
            idx++;
        }

    }

    public boolean run() {
        output.displayFrame(
            List.copyOf(wordDisplay),
            null,
            Set.copyOf(usedLetters),
            mistakes,
            maxMistakes
        );

        do {
            var cmd = this.input.getCommand();

            if (cmd.type() == GameInput.Command.Type.CHAR) {
                char data = Character.toLowerCase(cmd.data().charAt(0));
                if (alphabet.contains(data)) {
                    if (!usedLetters.contains(data)) {
                        usedLetters.add(data);
                        if (unsolvedMap.containsKey(data)) {
                            for (var idx : unsolvedMap.get(data)) {
                                wordDisplay.set(idx, data);
                            }
                            unsolvedMap.remove(data);
                        } else {
                            mistakes++;
                        }
                    }
                }
            }

            if (cmd.type() == GameInput.Command.Type.STRING && "clue".equals(cmd.data())) {
                showClue = true;
            }

            output.displayFrame(
                List.copyOf(wordDisplay),
                showClue ? clue : null,
                Set.copyOf(usedLetters),
                mistakes,
                maxMistakes
            );

        } while (!unsolvedMap.isEmpty() && mistakes != maxMistakes);

        return unsolvedMap.isEmpty();
    }


}