package exceptions;

public class InvalidParameterExecption extends ElectricaException {

    public InvalidParameterExecption() {
        super();
    }

    public InvalidParameterExecption(String message) {
        super(message);
    }

    public InvalidParameterExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterExecption(Throwable cause) {
        super(cause);
    }

    protected InvalidParameterExecption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
