package backend.academy.egfedo.util;

import java.security.SecureRandom;
import lombok.experimental.UtilityClass;


@UtilityClass
public class Utils {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        int x = RANDOM.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }

}
