package lab;

import lab.functions.Decoder;
import lab.functions.Encoder;
import lab.functions.Function;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);

        try {
            parser.parse();
        } catch (IllegalArgumentException ex) {
            Logger.log("Wrong arguments");
            return;
        }

        FileUtil fileUtil = new FileUtil();
        try (InputStream inputStream = fileUtil.getInputStream(parser.getInputFilename());
             OutputStream outputStream = fileUtil.getOutputStream(parser.getOutputFilename());
             InputStream configStream = fileUtil.getInputStream(parser.getConfigFilename())) {
            Config config = new Config(configStream);
            final ArrayList<Function> functions = new ArrayList<>();
            functions.add(new Encoder(config));
            functions.add(new Decoder(config));
            functions.add(new Encoder(config));
            functions.add(new Decoder(config));

            final int N = functions.size();
            final InputStream[] in = new PipedInputStream[N];
            final OutputStream[] out = new PipedOutputStream[N];
            for (int i = 0; i < N - 1; i++) {
                out[i] = new PipedOutputStream();
                in[i + 1] = new PipedInputStream((PipedOutputStream)out[i]);
            }

            Thread[] threads = new Thread[N];
            for (int i = 0; i < N; i++) {
                final int localI = i;
                threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Logger.log("Thread " + localI + " started");
                        try {
                            if (localI == 0) {
                                functions.get(localI).execute(inputStream, out[localI]);
                            } else if (localI == N - 1) {
                                functions.get(localI).execute(in[localI], outputStream);
                            } else {
                                functions.get(localI).execute(in[localI], out[localI]);
                            }

                            System.out.println("Thread " + localI);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                if (localI != 0) {
                                    in[localI].close();
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        Logger.log("Thread " + localI + " finished");
                    }
                });
                threads[i].start();
            }

            for (int i = 0; i < N; i++) {
                threads[i].join();
            }
        } catch (Exception ex) {
            Logger.log(ex.getMessage());
        }
    }
}
