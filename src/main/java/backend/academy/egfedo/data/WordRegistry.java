package backend.academy.egfedo.data;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

public final class WordRegistry {

    private final Map<Category, Map<Difficulty, List<Word>>> words = Map.of(
        Category.FRUIT,
        Map.of(
            Difficulty.EASY,
            Arrays.asList(
                new Word("авокадо", "Мягкий фрукт с кремовой мякотью"),
                new Word("манго", "Сочный тропический фрукт с ярким вкусом"),
                new Word("персик", "Сладкий фрукт с бархатистой кожурой"),
                new Word("лимон", "Кислый жёлтый цитрус, богат витамином C"),
                new Word("мандарин", "Маленький сладкий цитрус с лёгкой кожурой"),
                new Word("клубника", "Красная ягода с сочной и сладкой мякотью"),
                new Word("боярышник", "Мелкие ягоды с терпким вкусом, полезны для сердца"),
                new Word("абрикос", "Оранжевый фрукт с гладкой кожурой и косточкой"),
                new Word("апельсин", "Сладкий и сочный оранжевый цитрус"),
                new Word("виноград", "Мелкие ягоды на гроздьях, бывают сладкими или кислыми")
            ),
            Difficulty.MEDIUM,
            Arrays.asList(
                new Word("вишня", "Кисло-сладкая ягода для десертов"),
                new Word("яблоко", "Фрукт, часто символизирующий здоровье"),
                new Word("черешня", "Сладкая ягода, похожая на вишню"),
                new Word("маракуйя", "Экзотический фрукт с ароматной мякотью"),
                new Word("гранат", "Плод с множеством красных зёрнышек"),
                new Word("грейпфрут", "Крупный цитрус с горчинкой"),
                new Word("кумкват", "Маленький цитрус, едят с кожурой"),
                new Word("малина", "Ярко-ароматная ягода для варенья"),
                new Word("слива", "Фрукт с гладкой кожурой и косточкой"),
                new Word("помело", "Крупный и сладкий цитрус")
            ),
            Difficulty.HARD,
            Arrays.asList(
                new Word("дыня", "Сочная летняя ягода с нежной мякотью"),
                new Word("айва", "Твёрдый фрукт с терпким вкусом"),
                new Word("киви", "Маленький зелёный фрукт с пушистой кожурой"),
                new Word("банан", "Жёлтый плод с мягкой сладкой мякотью"),
                new Word("лайм", "Кислый зелёный цитрус для напитков"),
                new Word("груша", "Сладкий фрукт с мягкой текстурой"),
                new Word("арбуз", "Большой плод с сочной красной мякотью"),
                new Word("хурма", "Оранжевый фрукт с терпковатым вкусом"),
                new Word("инжир", "Фрукт с множеством мелких семечек внутри"),
                new Word("ананас", "Тропический плод с колючей кожурой")
            )
        ),
        Category.CITIES,
        Map.of(
            Difficulty.EASY,
            Arrays.asList(
                new Word("краков", "Исторический город Польши"),
                new Word("филадельфия", "Город в США, известен Декларацией независимости"),
                new Word("москва", "Столица России"),
                new Word("копенгаген", "Столица Дании"),
                new Word("лиссабон", "Столица Португалии"),
                new Word("флоренция", "Город искусства в Италии"),
                new Word("вашингтон", "Столица США"),
                new Word("монреаль", "Крупный город в Канаде"),
                new Word("барселона", "Крупный город Испании"),
                new Word("йоханнесбург", "Крупнейший город ЮАР")
            ),
            Difficulty.MEDIUM,
            Arrays.asList(
                new Word("минск", "Столица Беларуси"),
                new Word("токио", "Столица Японии"),
                new Word("милан", "Город моды в Италии"),
                new Word("бостон", "Исторический город США"),
                new Word("стамбул", "Крупнейший город Турции"),
                new Word("осло", "Столица Норвегии"),
                new Word("сидней", "Крупнейший город Австралии"),
                new Word("берлин", "Столица Германии"),
                new Word("мехико", "Столица Мексики"),
                new Word("торонто", "Крупнейший город Канады")
            ),
            Difficulty.HARD,
            Arrays.asList(
                new Word("рим", "Столица Италии"),
                new Word("баку", "Столица Азербайджана"),
                new Word("варшава", "Столица Польши"),
                new Word("дели", "Столица Индии"),
                new Word("гамбург", "Портовый город Германии"),
                new Word("киев", "Столица Украины"),
                new Word("париж", "Столица Франции"),
                new Word("лондон", "Столица Великобритании"),
                new Word("тбилиси", "Столица Грузии"),
                new Word("сочи", "Курортный город России")
            )
        )
    );

    @Getter private final Set<Character> alphabet =
        Set.of('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я');

    private final SecureRandom random = new SecureRandom();

    public Word getRandomWord(Category category, Difficulty difficulty) {
        Category realCategory = category;

        if (category == Category.RANDOM) {
            realCategory = Category.values()[random.nextInt(Category.values().length - 1)];
        }
        if (category == Category.RANDOM) {
            throw new IllegalStateException("Category must not be RANDOM at this point");
        }

        var list = words.get(realCategory).get(difficulty);
        return list.get(random.nextInt(list.size()));
    }
}
