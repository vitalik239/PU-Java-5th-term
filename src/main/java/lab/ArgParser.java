package lab;

import java.util.EnumMap;

class ArgParser {
    public enum ArgEnum {IN, OUT, CONF}

    private static final String SEPARATOR = ":";
    private static final int numberOfArgs = 3;

    private String[] args;
    private EnumMap<ArgEnum, String> files = new EnumMap<>(ArgEnum.class);

    ArgParser(String[] args) {
        this.args = args;
    }

    public void parse() throws IllegalArgumentException {
        if (args.length == numberOfArgs) {
            for (ArgEnum a : ArgEnum.values()) {
                for (String arg : args) {
                    if (arg.startsWith(a.toString().toLowerCase() + SEPARATOR)) {
                        if (files.containsKey(a)) {
                            throw new IllegalArgumentException("Two " + a.toString().toLowerCase() + " files.");
                        }
                        files.put(a, arg.split(SEPARATOR, 2)[1]);
                    }
                }
                if (!files.containsKey(a)) {
                    throw new IllegalArgumentException("Could not find " + a.toString().toLowerCase() + " file.");
                }
            }
        } else {
            throw new IllegalArgumentException("Wrong amount of arguments.");
        }
    }

    String getInputFilename() {
        return files.get(ArgEnum.IN);
    }

    String getOutputFilename() {
        return files.get(ArgEnum.OUT);
    }

    String getConfigFilename() {
        return files.get(ArgEnum.CONF);
    }
}
