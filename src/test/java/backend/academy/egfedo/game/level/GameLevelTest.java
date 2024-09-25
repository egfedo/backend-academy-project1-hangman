package backend.academy.egfedo.game.level;

import backend.academy.egfedo.data.Word;
import backend.academy.egfedo.io.GameInput;
import backend.academy.egfedo.io.LevelOutput;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameLevelTest {

    @Mock
    private GameInput input;
    @Mock
    private LevelOutput output;

    Set<Character> alphabet = new HashSet<>(
        Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к',
            'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц',
            'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'
        )
    );


    @Test
    @Timeout(5)
    void levelEndsSuccessfully() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "б"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "л"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "о"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "к")
        );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "а"), 5, alphabet);
        assertThat(level.run()).isTrue();

        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "а"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "б"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "в")
        );

        GameLevel level2 = new GameLevel(input, output,
            new Word("аааааааббббввв", "а"), 5, alphabet);
        assertThat(level2.run()).isTrue();
    }

    @Test
    @Timeout(5)
    void levelOutputsCorrectInfo() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "б"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "л"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "о"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "к")
            );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "а"), 5, alphabet);
        level.run();

        InOrder order = inOrder(output);
        order.verify(output).displayFrame(
            List.of('_', '_', '_', '_', '_', '_'),
            null,
            Set.of(),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', '_', '_', '_', '_', '_'),
            null,
            Set.of('я'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', '_', '_', '_', '_'),
            null,
            Set.of('я', 'б'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', '_', '_', '_'),
            null,
            Set.of('я', 'б', 'л'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', '_', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', 'к', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о', 'к'),
            0,
            5
        );
    }

    @Test
    @Timeout(5)
    void wrongInputDoesNotAffectAnything() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "б"),
            new GameInput.Command(GameInput.Command.Type.STRING, "абаб"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "л"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "о"),
            new GameInput.Command(GameInput.Command.Type.STRING, "jr"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "к")
        );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "а"), 5, alphabet);
        level.run();

        InOrder order = inOrder(output);
        order.verify(output).displayFrame(
            List.of('_', '_', '_', '_', '_', '_'),
            null,
            Set.of(),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', '_', '_', '_', '_', '_'),
            null,
            Set.of('я'),
            0,
            5
        );
        order.verify(output, times(2)).displayFrame(
            List.of('я', 'б', '_', '_', '_', '_'),
            null,
            Set.of('я', 'б'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', '_', '_', '_'),
            null,
            Set.of('я', 'б', 'л'),
            0,
            5
        );
        order.verify(output, times(2)).displayFrame(
            List.of('я', 'б', 'л', 'о', '_', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', 'к', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о', 'к'),
            0,
            5
        );
    }

    @Test
    @Timeout(5)
    void caseDoesNotMatter() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "Б"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "л"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "О"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "К")
        );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "а"), 5, alphabet);
        level.run();

        InOrder order = inOrder(output);
        order.verify(output).displayFrame(
            List.of('_', '_', '_', '_', '_', '_'),
            null,
            Set.of(),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', '_', '_', '_', '_', '_'),
            null,
            Set.of('я'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', '_', '_', '_', '_'),
            null,
            Set.of('я', 'б'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', '_', '_', '_'),
            null,
            Set.of('я', 'б', 'л'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', '_', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', 'к', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о', 'к'),
            0,
            5
        );
    }

    @Test
    @Timeout(5)
    void mistakesWork() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "г"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "б"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "л"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "и"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "о"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "к")
        );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "а"), 5, alphabet);
        level.run();

        InOrder order = inOrder(output);
        order.verify(output).displayFrame(
            List.of('_', '_', '_', '_', '_', '_'),
            null,
            Set.of(),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', '_', '_', '_', '_', '_'),
            null,
            Set.of('я'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', '_', '_', '_', '_', '_'),
            null,
            Set.of('я', 'г'),
            1,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', '_', '_', '_', '_'),
            null,
            Set.of('я', 'б', 'г'),
            1,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', '_', '_', '_'),
            null,
            Set.of('я', 'б', 'л', 'г'),
            1,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', '_', '_', '_'),
            null,
            Set.of('я', 'б', 'л', 'г', 'и'),
            2,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', '_', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о', 'г', 'и'),
            2,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', 'к', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о', 'к', 'г', 'и'),
            2,
            5
        );
    }

    @Test
    @Timeout(5)
    void repeatingLetterDoesNothing() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "б"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "л"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "о"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "к")
        );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "а"), 5, alphabet);
        level.run();

        InOrder order = inOrder(output);
        order.verify(output).displayFrame(
            List.of('_', '_', '_', '_', '_', '_'),
            null,
            Set.of(),
            0,
            5
        );
        order.verify(output, times(4)).displayFrame(
            List.of('я', '_', '_', '_', '_', '_'),
            null,
            Set.of('я'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', '_', '_', '_', '_'),
            null,
            Set.of('я', 'б'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', '_', '_', '_'),
            null,
            Set.of('я', 'б', 'л'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', '_', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', 'к', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о', 'к'),
            0,
            5
        );
    }

    @Test
    @Timeout(5)
    void clueDisplays() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "б"),
            new GameInput.Command(GameInput.Command.Type.STRING, "clue"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "л"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "о"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "к")
        );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "подсказка"), 5, alphabet);
        level.run();

        InOrder order = inOrder(output);
        order.verify(output).displayFrame(
            List.of('_', '_', '_', '_', '_', '_'),
            null,
            Set.of(),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', '_', '_', '_', '_', '_'),
            null,
            Set.of('я'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', '_', '_', '_', '_'),
            null,
            Set.of('я', 'б'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', '_', '_', '_', '_'),
            "подсказка",
            Set.of('я', 'б'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', '_', '_', '_'),
            "подсказка",
            Set.of('я', 'б', 'л'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', '_', 'о'),
            "подсказка",
            Set.of('я', 'б', 'л', 'о'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', 'к', 'о'),
            "подсказка",
            Set.of('я', 'б', 'л', 'о', 'к'),
            0,
            5
        );
    }

    @Test
    @Timeout(5)
    void gameEndsWhenHittingMaxMistakes() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "а"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "в"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "г"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "е"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "ё")
        );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "а"), 5, alphabet);

        assertThat(level.run()).isFalse();
    }

    @Test
    @Timeout(5)
    void wrongCharsDoNotAffectTheGame() {
        when(input.getCommand()).thenReturn(
            new GameInput.Command(GameInput.Command.Type.CHAR, "я"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "б"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "л"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "]"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "о"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "l"),
            new GameInput.Command(GameInput.Command.Type.CHAR, "к")
        );

        GameLevel level = new GameLevel(input, output,
            new Word("яблоко", "а"), 5, alphabet);
        level.run();

        InOrder order = inOrder(output);
        order.verify(output).displayFrame(
            List.of('_', '_', '_', '_', '_', '_'),
            null,
            Set.of(),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', '_', '_', '_', '_', '_'),
            null,
            Set.of('я'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', '_', '_', '_', '_'),
            null,
            Set.of('я', 'б'),
            0,
            5
        );
        order.verify(output, times(2)).displayFrame(
            List.of('я', 'б', 'л', '_', '_', '_'),
            null,
            Set.of('я', 'б', 'л'),
            0,
            5
        );
        order.verify(output, times(2)).displayFrame(
            List.of('я', 'б', 'л', 'о', '_', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о'),
            0,
            5
        );
        order.verify(output).displayFrame(
            List.of('я', 'б', 'л', 'о', 'к', 'о'),
            null,
            Set.of('я', 'б', 'л', 'о', 'к'),
            0,
            5
        );
    }
}
