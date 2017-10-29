package lab.functions;

import lab.Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Decoder implements Function {
    final int SEPARATOR;
    final int MAX_NUMBER;

    public Decoder() {
        SEPARATOR = 0;
        MAX_NUMBER = Integer.MAX_VALUE;
    }

    public Decoder(Config config) {
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
            int separator, count, symbol;
            do {
                separator = inputStream.read();
                if (separator != -1 & separator != SEPARATOR) {
                    System.err.println("Wrong separator");
                }
                count = inputStream.read();
                symbol = inputStream.read();

                for (int i = 0; i < count; i++) {
                    outputStream.write(symbol);
                }
            } while (separator != -1 & count != -1 & symbol != -1);

            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
