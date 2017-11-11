package lab;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import lab.exceptions.WrongClassFunctionResult;
import lab.functions.*;

public class Conveyor {
    private String[] classes;
    private String[] configs;
    private String input;
    private String result;

    Conveyor(String[] classes, String[] configs, String input) {
        this.classes = classes;
        this.configs = configs;
        this.input = input;
    }

    void run() throws Exception {
        Class<?> cls = Class.forName(classes[0]);
        Constructor<?> con = cls.getConstructor(String.class);
        Function prev_instance = (Function) con.newInstance(configs[0]);
        Method m = cls.getMethod("execute", String.class);
        Logger.log("Invoking " + prev_instance.toString());
        m.invoke(prev_instance, input);
        Logger.log("Completed " + prev_instance.toString());

        for (int i = 1; i < classes.length; i++) {
            cls = Class.forName(classes[i]);
            con = cls.getConstructor(String.class);
            Function instance = (Function)con.newInstance(configs[i]);

            if (instance.inputClass() != prev_instance.resultClass()) {
                throw new WrongClassFunctionResult
                        (instance.toString() + " supposed to get " + prev_instance.resultClass().toString());
            }

            Logger.log("Invoking " + instance.toString());
            instance.execute(prev_instance);
       //     m.invoke(instance, (Function)prev_instance);
            Logger.log("Completed " + instance.toString());
            prev_instance = instance;
        }

        result = prev_instance.getResult().toString();
        if (!result.equals(String.valueOf(input.length()))) {
            Logger.log("Expected " + String.valueOf(input.length()) + " got " + result);
        }
    }

    String getResult() {
        return result;
    }
}
