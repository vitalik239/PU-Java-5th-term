package lab.functions;

import lab.Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Decoder implements Function {
    final Config config;

    public Decoder() {
        config = null;
    }

    public Decoder(Config config) {
        this.config = config;
    }

    public void execute(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            int count, symbol;
            do {
                count = inputStream.read();
                symbol = inputStream.read();
                for (int i = 0; i < count; i++) {
                    outputStream.write(symbol);
                }
                System.out.println(count);
                System.out.println(symbol);
            } while (count != -1 & symbol != -1);

            outputStream.flush();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
