package lab;

import lab.exceptions.ConfigReaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private enum Params {SEPARATOR, MAX_NUMBER, LOGFILE}
    private static String CONFIG_SEPARATOR = ":";
    private Logger logger = Logger.getLogger("Config");

    private EnumMap<Params, String> params = new EnumMap<>(Params.class);

    Config(InputStream configStream) throws IOException {
        String s;
        BufferedReader reader = new BufferedReader(new InputStreamReader(configStream));

        while ((s = reader.readLine()) != null) {
            String[] keyValue = s.split(CONFIG_SEPARATOR, 2);

            Params key = null;
            try {
                key = Params.valueOf(keyValue[0].toUpperCase());
            } catch (IllegalArgumentException ex) {
                logger.log(Level.ALL, keyValue[0] + "is not in enum");
            }
            if (key == null) {
                continue;
            }
            System.out.println(key);

            if (params.containsKey(key)) {
                throw new ConfigReaderException("Duplicate key found");
            } else {
                params.put(key, keyValue[1]);
            }
        }

    }

    String getSeparator() {
        return params.get(Params.SEPARATOR);
    }

    String getLogfile() {
        return params.get(Params.LOGFILE);
    }

    String getMaxNumber() {
        return params.get(Params.MAX_NUMBER);
    }
}