package exceptions;

public abstract class ElectricaException extends Exception {
    public ElectricaException() {
        super();
    }

    public ElectricaException(String message) {
        super(message);
    }

    public ElectricaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElectricaException(Throwable cause) {
        super(cause);
    }

    protected ElectricaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
