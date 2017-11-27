package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.MyClasses.Adapter;
import lab.MyClasses.AdapterArrayList;
import lab.exceptions.WrongClassFunctionResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Slicer extends Function<String[]> {
    public Slicer(String configPath, Function previous) throws Exception {
        super(configPath, previous);

        outputConverter = new OutputConverter() {
            @Override
            public Object get(Class clz) throws Exception {
                if (clz == String[].class) {
                    return result;
                } else if (clz == AdapterArrayList.class) {
                    return new AdapterArrayList<String>() {
                        @Override
                        public String get(int index) {
                            return result[index];
                        }

                        @Override
                        public String[] get(int start, int finish) {
                            String[] toReturn = new String[finish - start];
                            for (int i = start; i < finish; i++)
                                toReturn[i - start] = result[i];
                            return toReturn;
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

    public Slicer(String configPath, String input) throws Exception {
        super(configPath, input);
        outputConverter = new OutputConverter() {
            @Override
            public Object get(Class clz) throws Exception {
                if (clz == String[].class) {
                    return result;
                } else if (clz == AdapterArrayList.class) {
                    return new AdapterArrayList<String>() {
                        @Override
                        public String get(int index) {
                            return result[index];
                        }

                        @Override
                        public String[] get(int start, int finish) {
                            String[] toReturn = new String[finish - start];
                            for (int i = start; i < finish; i++)
                                toReturn[i - start] = result[i];
                            return toReturn;
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

    public void execute()  throws Exception {
        if (previous_class == String.class) {
            String input = (String)previous_result;
            int length = Integer.parseInt(config.getLength());
            result = new String[(input.length() + length - 1) / length];
            for (int i = 0; i < input.length(); i += length) {
                result[i / length] = input.subSequence(i, Math.min(i + length, input.length())).toString();
            }
        }
    }

    public Set<Class> inputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(String.class);
        return result;
    }

    public Set<Class> outputClasses() {
        Set<Class> result = new HashSet<>();
        result.add(String[].class);
        result.add(AdapterArrayList.class);
        return result;
    }
}
