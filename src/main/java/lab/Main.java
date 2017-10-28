package lab;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lab.functions.Decoder;
import lab.functions.Encoder;
import lab.functions.Function;

import java.io.*;

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
            Function encoder = new Encoder(config);
            encoder.execute(inputStream, outputStream);
        } catch (Exception ex) {
            Logger.log(ex.getMessage());
        }
    }
}
