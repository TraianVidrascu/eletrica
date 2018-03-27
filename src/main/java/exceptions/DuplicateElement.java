package exceptions;

public class DuplicateElement extends ElectricaException {
    public DuplicateElement() {
    }

    public DuplicateElement(String message) {
        super(message);
    }

    public DuplicateElement(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateElement(Throwable cause) {
        super(cause);
    }

    public DuplicateElement(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
