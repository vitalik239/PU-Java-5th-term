package lab.functions;


import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.InputStream;
import java.util.Set;

public abstract class Function<S, T> {
    protected Class prev_class;
    protected Object prev = null;
    protected S previous_result = null;
    protected T result;
    protected final FunctionConfig config;
    protected InputConverter inputConverter;
    protected OutputConverter outputConverter;

    public Function(String configPath, S input) throws Exception {
        if (!configPath.isEmpty()) {
            InputStream configStream = FileUtil.getInputStream(configPath);
            this.config = new FunctionConfig(configStream);
            configStream.close();
        } else {
            this.config = null;
        }
        previous_result = input;
        Logger.log(this.toString() + " created");
    }

    public Function(String configPath, Function previous) throws Exception {
        if (!configPath.isEmpty()) {
            InputStream configStream = FileUtil.getInputStream(configPath);
            this.config = new FunctionConfig(configStream);
            configStream.close();
        } else {
            this.config = null;
        }

        Set<Class> intersection = this.inputClasses();
        intersection.retainAll(previous.outputClasses());
        if (!intersection.isEmpty()) {
            prev_class = intersection.iterator().next();
            prev = previous.outputConverter.get(prev_class);
        } else {
            throw new WrongClassFunctionResult("Expected smth else");
        }
        Logger.log(this.toString() + "created");
    }

    public interface InputConverter {
        void convert(Object prev, Class clz) throws Exception;
    }

    public interface OutputConverter {
        Object get(Class clz) throws Exception;
    }

    public abstract void execute() throws Exception;

    public abstract Set<Class> inputClasses();

    public abstract Set<Class> outputClasses();

    public Object getResult(Class clz) throws Exception {
        return outputConverter.get(clz);
    }

    public T getStandartResult() {
        return result;
    }
}
