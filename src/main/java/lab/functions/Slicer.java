package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;
import lab.Logger;
import lab.exceptions.WrongClassFunctionResult;

import java.io.IOException;
import java.io.InputStream;


public class Slicer implements Function<String, String[]> {
    private FunctionConfig config = null;
    private String[] result = null;

    public Slicer(String configPath) throws IOException {
        InputStream configStream = FileUtil.getInputStream(configPath);
        this.config = new FunctionConfig(configStream);
        configStream.close();
    }

    public void execute(String s) {
        int length = Integer.parseInt(config.getLength());
        result = new String[(s.length() + length - 1) / length];
        for (int i = 0; i < s.length(); i += length) {
            result[i / length] = s.subSequence(i, Math.min(i + length, s.length())).toString();
        }
    }

    public void execute(Function<?, String> previous)  throws Exception {
        if (this.inputClass() != previous.resultClass()) {
            throw new WrongClassFunctionResult
                    (this.toString() + " supposed to get " + previous.resultClass().toString());
        }

        Logger.log("Previous function was " + previous.toString());
        String s = previous.getResult();
        int length = Integer.parseInt(config.getLength());
        result = new String[(s.length() + length - 1) / length];
        for (int i = 0; i < s.length(); i += length) {
            result[i / length] = s.subSequence(i, Math.min(i + length, s.length())).toString();
        }
    }

    public Class inputClass() {
        return String.class;
    }

    public Class resultClass() {
        return String[].class;
    }

    public String[] getResult() {
        return result;
    }
}
