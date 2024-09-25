package backend.academy.egfedo.game.misc;

import backend.academy.egfedo.data.Word;
import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.LevelOutput;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LevelSupplierTest {

    @Mock
    WordRegistry registry;

    @Mock
    GameInput input;

    @Mock
    LevelOutput output;

    @Test
    void noNullableArguments() {
        assertThatThrownBy(
            () -> new LevelSupplier(null, input, registry)
        ).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(
            () -> new LevelSupplier(output, null, registry)
        ).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(
            () -> new LevelSupplier(output, input, null)
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void missingOptionsCheck() {
        var levelSupplier = new LevelSupplier(output, input, registry);
        assertThatThrownBy(
            () -> levelSupplier.getLevel(Map.of())
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
            () -> levelSupplier.getLevel(Map.of(GameMenu.OptionType.DIFFICULTY, "hard"))
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
            () -> levelSupplier.getLevel(Map.of(GameMenu.OptionType.CATEGORY, "fruits"))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void wrongOptionsCheck() {
        var levelSupplier = new LevelSupplier(output, input, registry);
        assertThatThrownBy(
            () -> levelSupplier.getLevel(
                Map.of(GameMenu.OptionType.DIFFICULTY, "hard",
                    GameMenu.OptionType.CATEGORY, "vegetables"
                ))
        ).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
            () -> levelSupplier.getLevel(
                Map.of(GameMenu.OptionType.DIFFICULTY, "eazy",
                    GameMenu.OptionType.CATEGORY, "fruits"
                ))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void correctCallsToRegistry() {
        when(registry.getRandomWord(Mockito.any(), Mockito.any()))
            .thenReturn(new Word("a", "a"));
        when(registry.alphabet()).thenReturn(Set.of('a'));

        var levelSupplier = new LevelSupplier(output, input, registry);

        levelSupplier.getLevel(
            Map.of(GameMenu.OptionType.CATEGORY, "fruits",
                GameMenu.OptionType.DIFFICULTY, "easy")
        );

        levelSupplier.getLevel(
            Map.of(GameMenu.OptionType.CATEGORY, "cities",
                GameMenu.OptionType.DIFFICULTY, "easy")
        );

        levelSupplier.getLevel(
            Map.of(GameMenu.OptionType.CATEGORY, "fruits",
                GameMenu.OptionType.DIFFICULTY, "hard")
        );

        InOrder order = inOrder(registry);
        order.verify(registry).getRandomWord(
            WordRegistry.Category.FRUIT,
            WordRegistry.Difficulty.EASY
        );
        order.verify(registry).getRandomWord(
            WordRegistry.Category.CITIES,
            WordRegistry.Difficulty.EASY
        );
        order.verify(registry).getRandomWord(
            WordRegistry.Category.FRUIT,
            WordRegistry.Difficulty.HARD
        );
    }
 }
