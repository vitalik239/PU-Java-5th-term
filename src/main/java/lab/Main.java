package lab;

import java.io.*;
import java.util.ArrayList;
import lab.FileUtil;
import org.apache.commons.io.IOUtils;

public class Main {
    enum ConfigParams {SEPARATOR, CLASSES, CONFIGS}
    enum FunctionsParams {LENGTH}

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

            Conveyor conveyor = new Conveyor(classes, configs, inputString);
            conveyor.run();
            String outputString = conveyor.getResult();

            outputStream.write(outputString.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.log(ex.getMessage());
        }
    }
}
