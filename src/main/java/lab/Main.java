package lab;

import lab.functions.Decoder;
import lab.functions.Encoder;
import lab.functions.Function;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);

        try {
            parser.parse();
        } catch (IllegalArgumentException ex) {
            logger.log(Level.ALL, ex.getMessage());
            return;
        }

        FileUtil fileUtil = new FileUtil();
        try (final InputStream inputStream = fileUtil.getInputStream(parser.getInputFilename());
             final OutputStream outputStream = fileUtil.getOutputStream(parser.getOutputFilename());
             InputStream configStream = fileUtil.getInputStream(parser.getConfigFilename())) {
            Config config = new Config(configStream);

            
        } catch (Exception ex) {
            logger.log(Level.ALL, ex.getMessage());
            ex.printStackTrace();
        }
    }
}
