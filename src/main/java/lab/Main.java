package lab;

import java.io.*;

import org.apache.commons.io.IOUtils;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);

        try {
            parser.parse();
        } catch (IllegalArgumentException ex) {
            Logger.log("Wrong arguments");
            return;
        }

        try (InputStream inputStream = FileUtil.getInputStream(parser.getInputFilename());
             OutputStream outputStream = FileUtil.getOutputStream(parser.getOutputFilename());
             InputStream configStream = FileUtil.getInputStream(parser.getConfigFilename())) {
            Config config = new Config(configStream);

            String inputString = IOUtils.toString(inputStream);
            String[] classes = config.getClasses();
            String[] configs = config.getConfigs();

            if (classes.length != configs.length) {
                throw new IllegalArgumentException("Wrong amount");
            }

            Pipeline pipeline = new Pipeline(classes, configs, inputString);
            Logger.log("Pipeline started");
            pipeline.run();
            Logger.log("Pipeline finished");

            String outputString = pipeline.getResult();

            outputStream.write(outputString.getBytes());
        } catch (Exception ex) {
            Logger.log(ex.getMessage());
        }
    }
}
