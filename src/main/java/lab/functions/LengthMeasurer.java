package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;

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

    public void execute(Function<?, String[]> previous) {
        Logger.log("Previous function was " + previous.toString());
        String[] strings = previous.getResult();
        result = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = strings[i].length();
        }

    }

    public Integer[] getResult() {
        return result;
    }
}
