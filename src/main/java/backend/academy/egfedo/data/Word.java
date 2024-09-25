package backend.academy.egfedo.data;

import java.util.Objects;

public record Word(String text, String clue) {
    public Word {
        Objects.requireNonNull(text, "Field 'text' cannot be null");
        Objects.requireNonNull(clue, "Field 'clue' cannot be null");

        if (text.isEmpty()) {
            throw new IllegalArgumentException("Field 'text' cannot be an empty string");
        }
        if (clue.isEmpty()) {
            throw new IllegalArgumentException("Field 'clue' cannot be an empty string");
        }
    }
}
