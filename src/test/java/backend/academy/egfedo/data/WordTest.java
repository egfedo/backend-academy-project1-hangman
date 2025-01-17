package backend.academy.egfedo.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WordTest {

    @Test
    @DisplayName("Constructor checks for null args")
    void nullCheck() {
        assertThatThrownBy(
            () -> new Word(null, "Some clue")
            ).isInstanceOf(NullPointerException.class)
            .hasMessageContaining("text");

        assertThatThrownBy(
            () -> new Word("Word", null)
        ).isInstanceOf(NullPointerException.class)
            .hasMessageContaining("clue");
    }

    @Test
    @DisplayName("Constructor checks for empty strings")
    void emptyCheck() {
        assertThatThrownBy(
            () -> new Word("", "Some clue")
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("text");

        assertThatThrownBy(
            () -> new Word("Word", "")
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("clue");
    }

}
