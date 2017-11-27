package lab.functions;


import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.MyClasses.Adapter;
import lab.exceptions.WrongClassFunctionResult;

import java.io.InputStream;
import java.util.Set;

public abstract class Function<T> {
    protected Object previous_result = null;
    protected Class previous_class = null;
    protected T result;
    protected final FunctionConfig config;
    protected OutputConverter outputConverter;

    public Function(String configPath, Object input) throws Exception {
        if (!configPath.isEmpty()) {
            InputStream configStream = FileUtil.getInputStream(configPath);
            this.config = new FunctionConfig(configStream);
            configStream.close();
        } else {
            this.config = null;
        }
        previous_class = input.getClass();
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
            previous_class = intersection.iterator().next();
            previous_result = previous.getResult(previous_class);
            Logger.log("Chosen interface: " + previous_class.toString());
        } else {
            throw new WrongClassFunctionResult("Expected smth else");
        }
        Logger.log(this.toString() + " created");
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
