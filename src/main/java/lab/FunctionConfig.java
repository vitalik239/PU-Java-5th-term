package lab;

import lab.exceptions.ConfigReaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;

public class FunctionConfig {
    private enum Params {LENGTH}
    private static String CONFIG_SEPARATOR = ":";

    private EnumMap<Params, String> params = new EnumMap<>(Params.class);

    public FunctionConfig(InputStream configStream) throws IOException {
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

    public String getLength() {
        return params.get(Params.LENGTH);
    }
}
