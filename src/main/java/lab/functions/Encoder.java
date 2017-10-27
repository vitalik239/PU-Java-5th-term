package lab.functions;

import lab.Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Encoder implements Function {
    final Config config;

    public Encoder() {
        config = null;
    }

    public Encoder(Config config) {
        this.config = config;
    }

    public void execute(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            int previous = 0, current;
            int count = 1;

            do {
                current = inputStream.read();
                System.out.println(count);
                System.out.println(current);

                if (current == previous) {
                    count += 1;
                } else if (previous != 0) {
                    outputStream.write(count);
                    outputStream.write(previous);
                    count = 1;
                }
                previous = current;
            } while (current != -1);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
