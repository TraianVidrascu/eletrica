package exceptions;

public class EmptyParameterException extends ElectricaException {
    public EmptyParameterException() {
    }

    public EmptyParameterException(String message) {
        super(message);
    }

    public EmptyParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyParameterException(Throwable cause) {
        super(cause);
    }

    public EmptyParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
