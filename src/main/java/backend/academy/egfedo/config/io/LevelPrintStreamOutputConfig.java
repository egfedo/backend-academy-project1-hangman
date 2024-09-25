package backend.academy.egfedo.config.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class LevelPrintStreamOutputConfig {

    private ConfigData configData = null;
    private final String filePath;

    public LevelPrintStreamOutputConfig(String path) {
        this.filePath = Objects.requireNonNull(path, "path cannot be null");
    }

    @SuppressWarnings({"MagicNumber"})
    @SuppressFBWarnings(value = {"PATH_TRAVERSAL_IN"}, justification = "Method checks if file exists")
    private void loadConfigFromFile() {
        var mapper = new ObjectMapper(new YAMLFactory());
        ConfigData configDataTmp = null;
        try {
            configDataTmp = mapper.readValue(new File(filePath), ConfigData.class);
        } catch (IOException e) {
            System.err.println("Could not read config file: " + filePath + ". Falling back to default config");

            configDataTmp = new ConfigData(49, 30, 17, 24,
                2, 4, 6, 8, 16,
                63, 3, 59, 7, 66,
                11, 9
            );
        } catch (Throwable e) {
            throw new RuntimeException("Could not read config file", e);
        } finally {
            configData = configDataTmp;
        }
    }

    public ConfigData getData() {
        if (configData == null) {
            loadConfigFromFile();
        }
        return configData;
    }

    @SuppressWarnings({"RecordComponentNumber"})
    public record ConfigData(
        int leftBlockWidth,
        int rightBlockWidth,
        int rowCount,
        int leftBlockCenter,
        int wordTitleRow,
        int wordRow,
        int clueTitleRow,
        int clueRow,
        int triesLine,
        int triesOffset,
        int poleBeginLine,
        int poleOffset,
        int stickmanBeginLine,
        int stickmanOffset,
        int lettersBeginLine,
        int lettersOffset
    ) {}
}
