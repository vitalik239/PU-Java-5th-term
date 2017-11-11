package lab.exceptions;

import java.io.IOException;

public class WrongClassFunctionResult extends IOException {
    public WrongClassFunctionResult(String reason) {
        super(reason);
    }
}
