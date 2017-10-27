package lab.exceptions;

import java.io.IOException;

public class ConfigReaderException extends IOException {
    public ConfigReaderException(String reason) {
        super(reason);
    }
}
