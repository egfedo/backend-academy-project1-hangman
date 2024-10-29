package backend.academy;

import backend.academy.egfedo.config.io.LevelPrintStreamOutputConfig;
import backend.academy.egfedo.data.Category;
import backend.academy.egfedo.data.Difficulty;
import backend.academy.egfedo.data.WordRegistry;
import backend.academy.egfedo.game.Game;
import backend.academy.egfedo.game.GameSession;
import backend.academy.egfedo.game.menu.GameMenu;
import backend.academy.egfedo.game.misc.LevelSupplier;
import backend.academy.egfedo.io.impl.LevelPrintStreamOutput;
import backend.academy.egfedo.io.impl.MenuPrintStreamOutput;
import backend.academy.egfedo.io.impl.ResultPrintStreamOutput;
import backend.academy.egfedo.io.impl.ScannerGameInput;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    @SuppressWarnings({"MultipleStringLiterals"})
    public static void main(String[] args) {
        Game.setUpSession();
    }
}
