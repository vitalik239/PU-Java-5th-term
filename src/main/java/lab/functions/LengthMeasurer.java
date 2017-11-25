package lab.functions;

import lab.exceptions.WrongClassFunctionResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LengthMeasurer extends Function<String[], Integer[]> {
    public LengthMeasurer(String configPath, Function<?, String[]> previous) throws Exception {
        super(configPath, previous);

        inputConverter = new InputConverter() {
            @Override
            public void convert(Object prev, Class clz) throws Exception {
                if (clz == String[].class) {
                    previous_result = (String[]) prev;
                } else if (clz == ArrayList.class) {
                    ArrayList list = (ArrayList)prev;
                    previous_result = new String[list.size()];
                    for (int i = 0; i < list.size(); i++)
                        previous_result[i] = (String)list.get(i);
                }
                else {
                    throw new WrongClassFunctionResult("Got " + clz.toString());
                }
            }
        };

        outputConverter = new OutputConverter() {
            @Override
            public Object get(Class clz) throws Exception {
                if (clz == Integer[].class) {
                    return result;
                } else {
                    throw new WrongClassFunctionResult("Not found " + clz.toString());
                }
            }
        };
    }

    public void execute() throws Exception {
        inputConverter.convert(prev, prev_class);
        result = new Integer[previous_result.length];
        for (int i = 0; i < previous_result.length; i++) {
            result[i] = previous_result[i].length();
        }
    }

    public Set<Class> inputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(String[].class);
        result.add(ArrayList.class);
        return result;
    }

    public Set<Class> outputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(Integer[].class);
        return result;
    }
}
