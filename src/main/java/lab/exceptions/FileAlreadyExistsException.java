package lab.exceptions;

import java.io.IOException;

public class FileAlreadyExistsException extends IOException {
    private static final String REASON = " (File already exists)";

    public FileAlreadyExistsException(String path) {
        super(path + REASON);
    }
}
