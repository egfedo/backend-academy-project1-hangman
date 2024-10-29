package backend.academy.egfedo.data;

public enum Difficulty {

    EASY(0, 11, "Легко"), MEDIUM(1, 7, "Средне"),
    HARD(2, 4, "Сложно"), RANDOM(-1, -1, "Случайно");

    public final int value;
    public final int maxErrors;
    private final String description;

    Difficulty(int value, int maxErrors, String description) {
        this.value = value;
        this.maxErrors = maxErrors;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
