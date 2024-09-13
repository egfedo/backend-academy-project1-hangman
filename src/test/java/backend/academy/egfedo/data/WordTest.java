package backend.academy.egfedo.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class WordTest {

    @Test
    @DisplayName("Word constructor checks for null args")
    void nullCheck() {
        assertThatThrownBy(
            () -> new Word(null, "Some clue")
            ).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("text");

        assertThatThrownBy(
            () -> new Word("Word", null)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("clue");
    }

}
