package backend.academy.egfedo.data;

import java.util.Objects;

public record Word(String text, String clue) {
    public Word {
        if (Objects.isNull(text)) {
            throw new IllegalArgumentException("Field 'text' cannot be null");
        }
        if (Objects.isNull(clue)) {
            throw new IllegalArgumentException("Field 'clue' cannot be null");
        }
    }
}
