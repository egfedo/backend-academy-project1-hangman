package backend.academy;

import backend.academy.egfedo.game.Game;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    @SuppressWarnings({"MultipleStringLiterals"})
    public static void main(String[] args) {
        Game.setUpSession();
    }
}
