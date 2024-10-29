package backend.academy.egfedo.game.menu;

import backend.academy.egfedo.data.Category;
import backend.academy.egfedo.data.Difficulty;
import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.MenuOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameMenuTest {

    @Mock
    MenuOutput output;

    @Mock
    GameInput input;

    static List<String> notEmptyList = List.of("option");

    @Test
    void nullCheck() {

        assertThatThrownBy(
            () -> new GameMenu<>(null, output, input, Category.class)
        ).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(
            () -> new GameMenu<>("", null, input, Category.class)
        ).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(
            () -> new GameMenu<>("", output, null, Category.class)
        ).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(
            () -> new GameMenu<>("", output, input, null)
        ).isInstanceOf(NullPointerException.class);
    }


    @Test
    @Timeout(3)
    void runReturnsCorrectly() {
        when(input.getCommand())
            .thenReturn(new GameInput.Command(GameInput.Command.Type.CHAR, "1"));
        var menu = new GameMenu<>("category", output, input, Category.class);

        menu.run();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output)
            .displayMenu("category", Arrays.stream(Category.values())
                .map(Objects::toString).toList());


    }

    private static Stream<Arguments> runReturnsCorrectValuesProvider() {
        return Stream.of(
            Arguments.of(
                "difficulty", Difficulty.class, 1, Difficulty.EASY
            ),
            Arguments.of(
                "category", Category.class, 2, Category.CITIES
            )
        );
    }

    @ParameterizedTest()
    @MethodSource("runReturnsCorrectValuesProvider")
    @Timeout(3)
    <T extends Enum<T>> void runReturnsCorrectValues(String menuName, Class<T> optionType,
        int inputInt, T expectedOutput) {
        when(input.getCommand())
            .thenReturn(new GameInput.Command(GameInput.Command.Type.CHAR,
                Integer.toString(inputInt)));

        var menu = new GameMenu<>(menuName, output, input, optionType);

        T enumVal = menu.run();

        assertThat(enumVal).isEqualTo(expectedOutput);

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).displayMenu(menuName,
            Arrays.stream(optionType.getEnumConstants())
                .map(Objects::toString).toList());


    }


    @Test
    @Timeout(3)
    void invalidInputDoesNothing() {
        when(input.getCommand())
            .thenReturn(
                new GameInput.Command(GameInput.Command.Type.STRING,
                "baba"),
                new GameInput.Command(GameInput.Command.Type.STRING,
                    "foobar"),
                new GameInput.Command(GameInput.Command.Type.STRING,
                    "10"),
                new GameInput.Command(GameInput.Command.Type.CHAR,
                    "1")
                );

        var menu = new GameMenu<>("menu", output, input, Category.class);

        menu.run();

        InOrder inOrder = inOrder(output);

        inOrder.verify(output, times(4))
            .displayMenu("menu",
                Arrays.stream(Category.values())
                    .map(Objects::toString).toList());
    }
}
