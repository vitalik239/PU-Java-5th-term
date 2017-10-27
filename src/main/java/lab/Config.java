package lab;

import lab.exceptions.ConfigReaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;

public class Config {
    private enum Params {SEPARATOR, MAX_NUMBER, LOGFILE}
    private static String CONFIG_SEPARATOR = ":";

    EnumMap<Params, String> params = new EnumMap<>(Params.class);

    Config(InputStream configStream) throws IOException {
        String s;
        BufferedReader reader = new BufferedReader(new InputStreamReader(configStream));

        while ((s = reader.readLine()) != null) {
            System.out.println(s);
            String[] keyValue = s.split(CONFIG_SEPARATOR, 2);
            Params key = Params.valueOf(keyValue[0]);
            System.out.println(key);

            if (params.containsKey(key)) {
                throw new ConfigReaderException("Duplicate key found");
            } else {
                params.put(Params.valueOf(keyValue[0]), keyValue[1]);
            }
        }

    }
}
