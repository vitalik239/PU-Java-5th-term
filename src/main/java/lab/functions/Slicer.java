package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Slicer extends Function<String, String[]> {
    public Slicer(String configPath, Function<?, String> previous) throws Exception {
        super(configPath, previous);
        inputConverter = new InputConverter() {
            @Override
            public void convert(Object prev, Class clz) throws Exception {
                if (clz == String.class) {
                    previous_result = (String) prev;
                } else {
                    throw new WrongClassFunctionResult("Got " + clz.toString());
                }
            }
        };

        outputConverter = new OutputConverter() {
            @Override
            public Object get(Class clz) throws Exception {
                if (clz == String[].class) {
                    return result;
                } else if (clz == ArrayList.class) {
                    ArrayList<String> list = new ArrayList<>();
                    for (String s : result) {
                        list.add(s);
                    }
                    return list;
                } else {
                    throw new WrongClassFunctionResult("Not found " + clz.toString());
                }
            }
        };
    }

    public Slicer(String configPath, String input) throws Exception {
        super(configPath, input);
        outputConverter = new OutputConverter() {
            @Override
            public Object get(Class clz) throws Exception {
                if (clz == String[].class) {
                    return result;
                } else if (clz == ArrayList.class) {
                    ArrayList<String> list = new ArrayList<>();
                    for (String s : result) {
                        list.add(s);
                    }
                    return list;
                } else {
                    throw new WrongClassFunctionResult("Not found " + clz.toString());
                }
            }
        };
    }

    public void execute()  throws Exception {
        if (previous_result == null)
            inputConverter.convert(prev, prev_class);
        int length = Integer.parseInt(config.getLength());
        result = new String[(previous_result.length() + length - 1) / length];
        for (int i = 0; i < previous_result.length(); i += length) {
            result[i / length] = previous_result.subSequence(i, Math.min(i + length, previous_result.length())).toString();
        }
    }

    public Set<Class> inputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(String.class);
        return result;
    }

    public Set<Class> outputClasses() {
        Set<Class> result = new HashSet<>();
   //     result.add(String[].class);
        result.add(ArrayList.class);
        return result;
    }
}
