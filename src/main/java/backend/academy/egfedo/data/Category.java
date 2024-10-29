package backend.academy.egfedo.data;

public enum Category {
    FRUIT("Фрукты"), CITIES("Города"),
    RANDOM("Случайно");

    final String description;

    Category(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
