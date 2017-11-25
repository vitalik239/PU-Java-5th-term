package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class Summator extends Function<Integer[], Integer> {
    public Summator(String configPath, Function<?, Integer[]> previous) throws Exception {
        super(configPath, previous);
        inputConverter = new InputConverter() {
            @Override
            public void convert(Object prev, Class clz) throws Exception {
                if (clz == Integer[].class) {
                    previous_result = (Integer[]) prev;
                } else {
                    throw new WrongClassFunctionResult("Got " + clz.toString());
                }
            }
        };

        outputConverter = new OutputConverter() {
            @Override
            public Object get(Class clz) throws Exception {
                if (clz == Integer.class) {
                    return result;
                } else {
                    throw new WrongClassFunctionResult("Not found " + clz.toString());
                }
            }
        };
    }

    public void execute() throws Exception {
        inputConverter.convert(prev, prev_class);
        result = 0;
        for (Integer anInt : previous_result) {
            result += anInt;
        }
    }

    public Set<Class> inputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(Integer[].class);
        return result;
    }

    public Set<Class> outputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(Integer.class);
        return result;
    }
}
