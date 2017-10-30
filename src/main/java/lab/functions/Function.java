package lab.functions;

import lab.FileUtil;
import lab.FunctionConfig;

import java.io.IOException;
import java.io.InputStream;

interface Function {
    void execute(Object f);
    Object getResult();
}
