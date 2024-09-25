package backend.academy.egfedo.game.menu;

import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.MenuOutput;
import java.util.HashMap;
import java.util.List;
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
            () -> new GameMenu(null, GameMenu.OptionType.CATEGORY,
                notEmptyList, notEmptyList, output, input)
        ).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(
            () -> new GameMenu("", null,
                notEmptyList, notEmptyList, output, input)
        ).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(
            () -> new GameMenu("", GameMenu.OptionType.CATEGORY,
                null, notEmptyList, output, input)
        ).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(
            () -> new GameMenu("", GameMenu.OptionType.CATEGORY,
                notEmptyList, null, output, input)
        ).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(
            () -> new GameMenu("", GameMenu.OptionType.CATEGORY,
                notEmptyList, notEmptyList, null, input)
        ).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(
            () -> new GameMenu("", GameMenu.OptionType.CATEGORY,
                notEmptyList, notEmptyList, output, null)
        ).isInstanceOf(NullPointerException.class);
    }

    private static Stream<Arguments> listContentsCheckProvider() {
        return Stream.of(
            Arguments.of(List.of(), List.of("arg")),
            Arguments.of(List.of("arg"), List.of()),
            Arguments.of(List.of("arg", "arg2"), List.of("argD"))
        );
    }

    @ParameterizedTest
    @MethodSource("listContentsCheckProvider")
    @Timeout(3)
    void listContentsCheck(List<String> options, List<String> optionData) {
        assertThatThrownBy(
            () -> new GameMenu("title", GameMenu.OptionType.CATEGORY,
                options, optionData, output, input)
        );
    }


    @Test
    @Timeout(3)
    void runReturnsCorrectly() {
        when(input.getCommand())
            .thenReturn(new GameInput.Command(GameInput.Command.Type.CHAR, "1"));
        var menu = new GameMenu("category", GameMenu.OptionType.CATEGORY,
            notEmptyList, notEmptyList, output, input);
        var options = new HashMap<GameMenu.OptionType, String>();

        menu.run(options);

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).displayMenu("category", notEmptyList);

        assertThat(options).containsEntry(GameMenu.OptionType.CATEGORY, "option");

    }

    private static Stream<Arguments> runReturnsCorrectValuesProvider() {
        return Stream.of(
            Arguments.of(
                "difficulty", GameMenu.OptionType.DIFFICULTY,
                notEmptyList, 1
            ),
            Arguments.of(
                "category", GameMenu.OptionType.CATEGORY,
                List.of("option1", "option2"), 2
            )
        );
    }

    @ParameterizedTest()
    @MethodSource("runReturnsCorrectValuesProvider")
    @Timeout(3)
    void runReturnsCorrectValues(String menuName, GameMenu.OptionType optionType,
        List<String> options, int inputInt) {
        when(input.getCommand())
            .thenReturn(new GameInput.Command(GameInput.Command.Type.CHAR,
                Integer.toString(inputInt)));
        var menu = new GameMenu(menuName, optionType,
            options, options, output, input);
        var optionMap = new HashMap<GameMenu.OptionType, String>();

        menu.run(optionMap);

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).displayMenu(menuName, options);

        assertThat(optionMap).containsEntry(
            optionType,
            options.get(inputInt-1)
        );

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

        var menu = new GameMenu("menu", GameMenu.OptionType.CATEGORY,
            notEmptyList, notEmptyList, output, input);

        menu.run(new HashMap<>());

        InOrder inOrder = inOrder(output);

        inOrder.verify(output, times(4)).displayMenu("menu", notEmptyList);
    }
}
