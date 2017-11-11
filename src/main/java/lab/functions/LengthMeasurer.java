package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.IOException;
import java.io.InputStream;

public class LengthMeasurer extends Function<String[], Integer[]> {
    public LengthMeasurer(String configPath, Function<?, String[]> previous) throws Exception {
        super(configPath, previous);
    }

    public void execute() throws Exception {
        result = new Integer[previous_result.length];
        for (int i = 0; i < previous_result.length; i++) {
            result[i] = previous_result[i].length();
        }
    }

    public Class inputClass() {
        return String[].class;
    }
}
