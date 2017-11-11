package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.IOException;
import java.io.InputStream;

public class Summator extends Function<Integer[], Integer> {
    public Summator(String configPath, Function<?, Integer[]> previous) throws Exception {
        super(configPath, previous);
    }

    public void execute() throws Exception {
        result = 0;
        for (Integer anInt : previous_result) {
            result += anInt;
        }
    }

    public Class inputClass() {
        return Integer[].class;
    }
}
