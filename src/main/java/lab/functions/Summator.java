package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;

import java.io.IOException;
import java.io.InputStream;

public class Summator implements Function {
    private Integer result = 0;
    private FunctionConfig config = null;

    public Summator(String configPath) throws IOException {
        InputStream configStream = FileUtil.getInputStream(configPath);
        this.config = new FunctionConfig(configStream);
        configStream.close();
    }

    public void execute(Object previous) {
        System.err.println(previous.getClass());
        Integer[] ints = (Integer[])((Function)previous).getResult();
        for (Integer anInt : ints) {
            result += anInt;
        }
    }

    public Integer getResult() {
        return result;
    }
}
