package lab;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
        Object prev_instance = con.newInstance(configs[0]);
        Method m = cls.getMethod("execute", String.class);
        m.invoke(prev_instance, input);

        for (int i = 1; i < classes.length; i++) {
            cls = Class.forName(classes[i]);
            con = cls.getConstructor(String.class);
            Object instance = con.newInstance(configs[i]);
            m = cls.getMethod("execute", Object.class);
            m.invoke(instance, prev_instance);
            prev_instance = instance;
        }
        m = cls.getMethod("getResult");
        result = m.invoke(prev_instance).toString();
        if (!result.equals(String.valueOf(input.length()))) {
            Logger.log("Expected " + String.valueOf(input.length()) + " got " + result);
        }
    }

    String getResult() {
        return result;
    }
}
