package lab;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import lab.exceptions.WrongClassFunctionResult;
import lab.functions.*;

public class Pipeline {
    private String[] classes;
    private String[] configs;
    private String input;
    private String result;

    Pipeline(String[] classes, String[] configs, String input) {
        this.classes = classes;
        this.configs = configs;
        this.input = input;
    }

    void run() throws Exception {
        Class<?> cls = Class.forName(classes[0]);
        Constructor<?> con = cls.getConstructor(String.class, String.class);
        Function prev_instance = (Function) con.newInstance(configs[0], input);
        Logger.log("Invoking " + prev_instance.toString());
        prev_instance.execute();
        Logger.log("Completed " + prev_instance.toString());

        for (int i = 1; i < classes.length; i++) {
            cls = Class.forName(classes[i]);
            con = cls.getConstructor(String.class, Function.class);

            Function instance = (Function)con.newInstance(configs[i], prev_instance);

            Logger.log("Invoking " + instance.toString());
            instance.execute();
            Logger.log("Completed " + instance.toString());

            prev_instance = instance;
        }

        result = prev_instance.getResult(Integer.class).toString();
        if (!result.equals(String.valueOf(input.length()))) {
            Logger.log("Expected " + String.valueOf(input.length()) + " got " + result);
        }
    }

    String getResult() {
        return result;
    }
}
