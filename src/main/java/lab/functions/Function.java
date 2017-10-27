package lab.functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Function {
    void execute(InputStream inputStream, OutputStream outputStream) throws IOException;
}
