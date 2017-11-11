package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.IOException;
import java.io.InputStream;


public class Slicer extends Function<String, String[]> {
    public Slicer(String configPath, Function<?, String> previous) throws Exception {
        super(configPath, previous);
    }

    public Slicer(String configPath, String input) throws Exception {
        super(configPath, input);
    }

    public void execute(String s) {
        int length = Integer.parseInt(config.getLength());
        result = new String[(s.length() + length - 1) / length];
        for (int i = 0; i < s.length(); i += length) {
            result[i / length] = s.subSequence(i, Math.min(i + length, s.length())).toString();
        }
    }

    public void execute()  throws Exception {
        int length = Integer.parseInt(config.getLength());
        result = new String[(previous_result.length() + length - 1) / length];
        for (int i = 0; i < previous_result.length(); i += length) {
            result[i / length] = previous_result.subSequence(i, Math.min(i + length, previous_result.length())).toString();
        }
    }

    public Class inputClass() {
        return String.class;
    }
}
