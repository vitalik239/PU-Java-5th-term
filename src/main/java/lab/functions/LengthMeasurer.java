package lab.functions;

import lab.MyClasses.AdapterArrayList;
import lab.MyClasses.AdapterDoubleArray;
import lab.exceptions.WrongClassFunctionResult;

import java.util.HashSet;
import java.util.Set;

public class LengthMeasurer extends Function<Integer[]> {
    public LengthMeasurer(String configPath, Function previous) throws Exception {
        super(configPath, previous);

        outputConverter = new OutputConverter() {
            @Override
            public Object get(Class clz) throws Exception {
                if (clz == Integer[].class) {
                    return result;
                } else if (clz == AdapterArrayList.class) {
                    return new AdapterArrayList<Integer>() {
                        @Override
                        public Integer get(int index) {
                            return result[index];
                        }

                        @Override
                        public Integer[] get(int start, int finish) {
                            Integer[] toReturn = new Integer[finish - start];
                            for (int i = start; i < finish; i++)
                                toReturn[i - start] = result[i];
                            return toReturn;
                        }

                        @Override
                        public int size() {
                            return result.length;
                        }
                    };
                } else if (clz == AdapterDoubleArray.class) {
                    return new AdapterDoubleArray() {
                        @Override
                        public double get(int index) {
                            return (double)result[index];
                        }

                        @Override
                        public int size() {
                            return result.length;
                        }
                    };
                } else {
                    throw new WrongClassFunctionResult("Not found " + clz.toString());
                }
            }
        };
    }

    public void execute() throws Exception {
        if (previous_class == String[].class) {
            String[] input = (String[])previous_result;
            result = new Integer[input.length];
            for (int i = 0; i < input.length; i++) {
                result[i] = input[i].length();
            }
        } else if (previous_class == AdapterArrayList.class &
                previous_result instanceof AdapterArrayList) {
            AdapterArrayList<String> input = (AdapterArrayList<String>)previous_result;
            result = new Integer[input.size()];
            for (int i = 0; i < input.size(); i++) {
                result[i] = input.get(i).length();
            }
        }
    }

    public Set<Class> inputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(String[].class);
        result.add(AdapterArrayList.class);
        return result;
    }

    public Set<Class> outputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(Integer[].class);
        result.add(AdapterArrayList.class);
        result.add(AdapterDoubleArray.class);
        return result;
    }
}
