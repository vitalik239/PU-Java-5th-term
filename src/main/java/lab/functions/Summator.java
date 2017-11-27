package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.MyClasses.Adapter;
import lab.MyClasses.AdapterArrayList;
import lab.MyClasses.AdapterDoubleArray;
import lab.exceptions.WrongClassFunctionResult;

import java.util.HashSet;
import java.util.Set;

public class Summator extends Function<Integer> {
    public Summator(String configPath, Function previous) throws Exception {
        super(configPath, previous);
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
        if (previous_class == Integer[].class) {
            Integer[] input = (Integer[]) previous_result;
            result = 0;
            for (Integer anInt : input) {
                result += anInt;
            }
        } else if (previous_class == AdapterArrayList.class &
                previous_result instanceof AdapterArrayList) {
            AdapterArrayList<Integer> input = (AdapterArrayList<Integer>) previous_result;
            result = 0;
            for (int i = 0; i < input.size(); i++) {
                result += input.get(i);
            }
        } else if (previous_class == AdapterDoubleArray.class &
                previous_result instanceof  AdapterDoubleArray) {
            AdapterDoubleArray input = (AdapterDoubleArray)previous_result;
            result = 0;
            for (int i = 0; i < input.size(); i++) {
                result += (int)input.get(i);
            }
        }
    }

    public Set<Class> inputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(Integer[].class);
        result.add(AdapterArrayList.class);
        result.add(AdapterDoubleArray.class);
        return result;
    }

    public Set<Class> outputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(Integer.class);
        return result;
    }
}
