package lab.functions;

import lab.Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Encoder implements Function {
    final int SEPARATOR;
    final int MAX_NUMBER;

    public Encoder() {
        SEPARATOR = 0;
        MAX_NUMBER = Integer.MAX_VALUE;
    }

    public Encoder(Config config) {
        SEPARATOR = config.getSeparator().charAt(0);
        int max = 0;
        try {
            max = Integer.parseInt(config.getMaxNumber());
        } catch (NumberFormatException ex) {
            max = Integer.MAX_VALUE;
        }
        MAX_NUMBER = max;
    }

    public void execute(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            int previous = 0, current;
            int count = 1;

            do {
                current = inputStream.read();
                if (current == previous & count != MAX_NUMBER) {
                    count += 1;
                } else if (previous != 0) {
                    if (SEPARATOR != 0)
                        outputStream.write(SEPARATOR);
                    outputStream.write(count);
                    outputStream.write(previous);

                    count = 1;
                }
                previous = current;
            } while (current != -1);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
