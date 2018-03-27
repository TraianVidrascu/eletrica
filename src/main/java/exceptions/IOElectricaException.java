package exceptions;

public class IOElectricaException extends ElectricaException {
    public IOElectricaException() {
    }

    public IOElectricaException(String message) {
        super(message);
    }

    public IOElectricaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IOElectricaException(Throwable cause) {
        super(cause);
    }

    public IOElectricaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
