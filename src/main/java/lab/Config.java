package lab;

import lab.exceptions.ConfigReaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;

public class Config {
    private enum Params {SEPARATOR, CLASSES, CONFIGS}
    private static String CONFIG_SEPARATOR = ":";

    EnumMap<Params, String> params = new EnumMap<>(Params.class);

    Config(InputStream configStream) throws IOException {
        String s;
        BufferedReader reader = new BufferedReader(new InputStreamReader(configStream));

        while ((s = reader.readLine()) != null) {
            String[] keyValue = s.split(CONFIG_SEPARATOR, 2);
            Params key = null;
            try {
                key = Params.valueOf(keyValue[0].toUpperCase());
            } catch (IllegalArgumentException ex) {
                Logger.log("Cannot recognize this key: " + keyValue[0]);
            }
            if (key == null) {
                continue;
            }

            if (params.containsKey(key)) {
                throw new ConfigReaderException("Duplicate key found");
            } else {
                params.put(key, keyValue[1]);
            }
        }

    }

    public String[] getClasses() {
        return params.get(Params.CLASSES).split(params.get(Params.SEPARATOR));
    }

    public String[] getConfigs() {
        return params.get(Params.CONFIGS).split(params.get(Params.SEPARATOR));
    }
}
