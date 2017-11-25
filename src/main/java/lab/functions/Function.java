package lab.functions;


import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.InputStream;
import java.util.Set;

public abstract class Function<S, T> {
    protected final S previous_result;
    protected T result;
    protected final FunctionConfig config;

    public Function(String configPath, S input) throws Exception {
        if (!configPath.isEmpty()) {
            InputStream configStream = FileUtil.getInputStream(configPath);
            this.config = new FunctionConfig(configStream);
            configStream.close();
        } else {
            this.config = null;
        }
        previous_result = input;
        Logger.log(this.toString() + "created");
    }

    public Function(String configPath, Function previous) throws Exception {
        if (!configPath.isEmpty()) {
            InputStream configStream = FileUtil.getInputStream(configPath);
            this.config = new FunctionConfig(configStream);
            configStream.close();
        } else {
            this.config = null;
        }

        if (previous.resultClass().contains(this.inputClass())) {
            previous_result = previous.getResult(this.inputClass());
        } else {
            throw new WrongClassFunctionResult("Expected smth else");
        }
        Logger.log(this.toString() + "created");
    }

    public static abstract class inputConverter {
        abstract T convert
    }

    public static abstract class outputConverter {
        abstract Object convert(Class cls);
    }

    public abstract void execute() throws Exception;

    public abstract Set<Class> inputClasses();

    public abstract Set<Class> outputClasses();

    public Object getResult(Class clz) {
        return outputConverter.convert(clz);
    }

    public T getStandartResult() {
        return result;
    }
}
