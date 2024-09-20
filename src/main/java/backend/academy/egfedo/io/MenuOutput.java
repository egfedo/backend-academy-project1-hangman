package backend.academy.egfedo.io;

import java.util.List;

public interface MenuOutput {
    void displayMenu(
        String title,
        List<String> options
    );
}
