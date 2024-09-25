package backend.academy.egfedo.io.impl;

import backend.academy.egfedo.config.io.LevelPrintStreamOutputConfig;
import backend.academy.egfedo.io.LevelOutput;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class LevelPrintStreamOutput implements LevelOutput {

    private final PrintStream printStream;
    private final LevelPrintStreamOutputConfig.ConfigData config;

    private enum Template {
        EMPTY,
        WORD,
        CLUE,
        EMPTY_CLUE,
        LOGO,
        BAR
    }

    private final Map<Template, String> stringTemplates;

    private final List<String> stickman = List.of(
        " O ",
        "/|\\",
        "'|'",
        "/ \\",
        "' '"
    );

    private final List<String> pole = List.of(
        "o-------o",
        "| /     |",
        "|/      |",
        "|       |",
        "|",
        "|",
        "|",
        "|",
        "|",
        "|",
        "|\\",
        "o------------"
    );
    private final List<List<String>> letters = List.of(
        Arrays.asList("а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й"),
        Arrays.asList("к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф"),
        Arrays.asList("х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я")
    );

    public LevelPrintStreamOutput(PrintStream printStream, LevelPrintStreamOutputConfig.ConfigData config) {
        this.printStream = printStream;
        this.config = config;

        this.stringTemplates = Map.of(
            Template.EMPTY,
            " ".repeat(config.leftBlockWidth()) + "|" + " ".repeat(config.rightBlockWidth()),
            Template.WORD,
            "                    [ Слово ]                    |"  + " ".repeat(config.rightBlockWidth()),
            Template.CLUE,
            "                  [ Подсказка ]                  |" + " ".repeat(config.rightBlockWidth()),
            Template.EMPTY_CLUE,
            "                (Не активирована)                |",
            Template.LOGO,
            """
                                                             \s
                                            |__| _  _  _  _  _  _ \s
                                            |  |(_|| )(_)|||(_|| )\s
                                                      _/          \s
                -------------------------------------------------o------------------------------""",
            Template.BAR, "-------------------------------------------------o------------------------------"
        );
    }

    @Override
    public void displayFrame(
        List<Character> word,
        String clue,
        Set<Character> usedLetters,
        int mistakes,
        int maxMistakes
    ) {
        List<StringBuilder> screen = new ArrayList<>(List.of(
            new StringBuilder(stringTemplates.get(Template.LOGO))

        ));

        for (int i = 0; i < config.rowCount(); i++) {
            screen.add(new StringBuilder(stringTemplates.get(Template.EMPTY)));
        }
        screen.add(new StringBuilder(stringTemplates.get(Template.BAR)));
        screen.get(config.wordTitleRow()).setLength(0);
        screen.get(config.wordTitleRow()).append(stringTemplates.get(Template.WORD));
        screen.get(config.clueTitleRow()).setLength(0);
        screen.get(config.clueTitleRow()).append(stringTemplates.get(Template.CLUE));

        String displayWord = String.join(
            "   ",
            word.stream()
                .map(Character::toUpperCase)
                .map(Object::toString)
                .toList()
        );

        int offset = config.leftBlockCenter() - displayWord.length() / 2;
        screen.get(config.wordRow()).replace(offset, offset + displayWord.length(), displayWord);

        var currentStickman = new ArrayList<>(
            stickman.stream().map(StringBuilder::new).toList()
        );
        int row = currentStickman.size() - 1;
        int idx = 2;
        for (int i = 0; i < maxMistakes - mistakes; i++) {
            while (currentStickman.get(row).charAt(idx) == ' ') {
                idx--;
                if (idx == -1) {
                    idx = 2;
                    row--;
                }
            }
            currentStickman.get(row).setCharAt(idx, ' ');
        }

        String triesStr = mistakes + "/" + maxMistakes;
        screen.get(config.triesLine())
            .replace(config.triesOffset(),
                config.triesOffset() + triesStr.length(),
                triesStr);


        int lineNum = config.poleBeginLine();
        for (var part : pole) {
            screen.get(lineNum).replace(config.poleOffset(), config.poleOffset() + part.length(), part);
            lineNum += 1;
        }
        lineNum = config.stickmanBeginLine();
        for (var part : currentStickman) {
            screen.get(lineNum).replace(config.stickmanOffset(),
                config.stickmanOffset() + part.length(),
                part.toString());
            lineNum += 1;
        }

        lineNum = config.lettersBeginLine();
        for (var line : letters) {
            String unformattedString = String.join("  ", line);
            String lettersString = String.join("  ", line.stream()
                .map(
                    c -> {
                        if (usedLetters.contains(c.charAt(0))) {
                            return "\033[0;1;4m" + Character.toUpperCase(c.charAt(0)) + "\033[0;0m";
                        }
                        return Character.toString(Character.toUpperCase(c.charAt(0)));
                    }
                ).toList()
            );
            screen.get(lineNum)
                .replace(config.lettersOffset(),
                    config.lettersOffset() + unformattedString.length(),
                    lettersString);
            lineNum += 2;
        }

        if (Objects.isNull(clue)) {
            String template = stringTemplates.get(Template.EMPTY_CLUE);
            screen.get(config.clueRow()).replace(0, template.length(), template);
        } else {
            int clueOffset = config.leftBlockCenter() - clue.length() / 2;
            screen.get(config.clueRow()).replace(clueOffset, clueOffset + clue.length(), clue);
        }
        for (var line : screen) {
            printStream.println(line);
        }
        printStream.print("> ");
    }
}
