package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.IOException;
import java.io.InputStream;

public class LengthMeasurer implements Function<String[], Integer[]> {
    private Integer[] result = null;
    private FunctionConfig config = null;

    public LengthMeasurer(String configPath) throws IOException {
        InputStream configStream = FileUtil.getInputStream(configPath);
        this.config = new FunctionConfig(configStream);
        configStream.close();
    }

    public void execute(Function<?, String[]> previous) throws Exception {
        if (this.inputClass() != previous.resultClass()) {
            throw new WrongClassFunctionResult
                    (this.toString() + " supposed to get " + this.resultClass().toString());
        }

        Logger.log("Previous function was " + previous.toString());
        String[] strings = previous.getResult();
        result = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = strings[i].length();
        }

    }

    public Class inputClass() {
        return String[].class;
    }

    public Class resultClass() {
        return Integer[].class;
    }

    public Integer[] getResult() {
        return result;
    }
}
