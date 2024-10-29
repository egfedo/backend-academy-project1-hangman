package backend.academy.egfedo.game.misc;

import backend.academy.egfedo.data.Category;
import backend.academy.egfedo.data.Difficulty;
import backend.academy.egfedo.data.Word;
import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.LevelOutput;
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
    void correctCallsToRegistry() {
        when(registry.getRandomWord(Mockito.any(), Mockito.any()))
            .thenReturn(new Word("a", "a"));
        when(registry.alphabet()).thenReturn(Set.of('a'));

        var levelSupplier = new LevelSupplier(output, input, registry);

        levelSupplier.getLevel(
            Category.FRUIT,
            Difficulty.EASY
        );

        levelSupplier.getLevel(
            Category.CITIES,
            Difficulty.EASY
        );

        levelSupplier.getLevel(
           Category.FRUIT, Difficulty.HARD
        );

        InOrder order = inOrder(registry);
        order.verify(registry).getRandomWord(
            Category.FRUIT,
            Difficulty.EASY
        );
        order.verify(registry).getRandomWord(
            Category.CITIES,
            Difficulty.EASY
        );
        order.verify(registry).getRandomWord(
            Category.FRUIT,
            Difficulty.HARD
        );
    }
 }
