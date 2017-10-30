package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;

import java.io.IOException;
import java.io.InputStream;

public class LengthMeasurer implements Function {
    private Integer[] result = null;
    private FunctionConfig config = null;

    public LengthMeasurer(String configPath) throws IOException {
        InputStream configStream = FileUtil.getInputStream(configPath);
        this.config = new FunctionConfig(configStream);
        configStream.close();
    }

    public void execute(Object previous) {
        System.err.println(previous.getClass());

        String[] strings = (String[])((Function)previous).getResult();
        result = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = strings[i].length();
        }

    }

    public Integer[] getResult() {
        return result;
    }
}
