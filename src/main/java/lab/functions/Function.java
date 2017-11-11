package lab.functions;


import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.InputStream;

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
    }

    public Function(String configPath, Function<?, S> previous) throws Exception {
        if (!configPath.isEmpty()) {
            InputStream configStream = FileUtil.getInputStream(configPath);
            this.config = new FunctionConfig(configStream);
            configStream.close();
        } else {
            this.config = null;
        }

        if (previous.resultClass() != this.inputClass()) {
            throw new WrongClassFunctionResult("Expected smth else");
        } else {
            previous_result = previous.getResult();
        }
    }

    public abstract void execute() throws Exception;

    public abstract Class inputClass();

    public Class resultClass() {
        return result.getClass();
    }

    public T getResult() {
        return result;
    }
}
