package backend.academy.egfedo.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WordRegistryTest {

    List<String> fruitWords = Arrays.asList(
        "дыня", "айва", "киви", "банан", "лайм",
        "груша", "арбуз", "хурма", "инжир",
        "ананас", "вишня", "яблоко", "черешня",
        "маракуйя", "гранат", "грейпфрут",
        "кумкват", "малина", "слива", "помело",
        "авокадо", "манго", "персик", "лимон",
        "мандарин", "клубника", "боярышник",
        "абрикос", "апельсин", "виноград"
    );

    List<String> fruitHard = Arrays.asList(
        "дыня", "айва", "киви", "банан", "лайм",
        "груша", "арбуз", "хурма", "инжир", "ананас"
    );

    List<String> cityWords = Arrays.asList(
        "рим", "баку", "варшава", "дели", "гамбург",
        "киев", "париж", "лондон", "тбилиси", "сочи",
        "минск", "токио", "милан", "бостон", "стамбул",
        "осло", "сидней", "берлин", "мехико", "торонто",
        "краков", "филадельфия", "москва", "копенгаген",
        "лиссабон", "флоренция", "вашингтон", "монреаль",
        "барселона", "йоханнесбург"
    );

    List<String> hardWords = Arrays.asList(
        "дыня", "айва", "киви", "банан", "лайм",
        "груша", "арбуз", "хурма", "инжир", "ананас",
        "рим", "баку", "варшава", "дели", "гамбург",
        "киев", "париж", "лондон", "тбилиси", "сочи"
    );


    WordRegistry wordRegistry = new WordRegistry();

    @RepeatedTest(20)
    @DisplayName("Category of the word matches the argument")
    void categoryMatchesTheArgument() {
        assertThat(
            wordRegistry.getRandomWord(WordRegistry.Category.FRUIT, null)
                .text()
        ).isIn(fruitWords);

        assertThat(
            wordRegistry.getRandomWord(WordRegistry.Category.CITIES, null)
                .text()
        ).isIn(cityWords);
    }

    @RepeatedTest(20)
    @DisplayName("Difficulty of the word matches the argument")
    void difficultyMatchesTheArgument() {
        assertThat(
            wordRegistry.getRandomWord(WordRegistry.Category.FRUIT, WordRegistry.Difficulty.HARD)
                .text()
        ).isIn(fruitHard);

        assertThat(
            wordRegistry.getRandomWord(null, WordRegistry.Difficulty.HARD)
                .text()
        ).isIn(hardWords);
    }

}
