package exceptions;

public class InvalidCharacterException extends ElectricaException{
    public InvalidCharacterException() {
    }

    public InvalidCharacterException(String message) {
        super(message);
    }

    public InvalidCharacterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCharacterException(Throwable cause) {
        super(cause);
    }

    public InvalidCharacterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
