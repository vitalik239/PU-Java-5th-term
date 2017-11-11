package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.IOException;
import java.io.InputStream;

public class Summator implements Function<Integer[], Integer> {
    private Integer result = 0;
    private FunctionConfig config = null;

    public Summator(String configPath) throws IOException {
        InputStream configStream = FileUtil.getInputStream(configPath);
        this.config = new FunctionConfig(configStream);
        configStream.close();
    }

    public void execute(Function<?, Integer[]> previous) throws Exception {
        if (this.inputClass() != previous.resultClass()) {
            throw new WrongClassFunctionResult
                    (this.toString() + " supposed to get " + previous.resultClass().toString());
        }

        Logger.log("Previous function was " + previous.toString());
        Integer[] ints = previous.getResult();
        for (Integer anInt : ints) {
            result += anInt;
        }
    }


    public Class inputClass() {
        return Integer[].class;
    }

    public Class resultClass() {
        return Integer.class;
    }

    public Integer getResult() {
        return result;
    }
}
