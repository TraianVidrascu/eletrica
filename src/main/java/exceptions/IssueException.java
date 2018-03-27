package exceptions;

public class IssueException extends ElectricaException {
    public IssueException() {
    }

    public IssueException(String message) {
        super(message);
    }

    public IssueException(String message, Throwable cause) {
        super(message, cause);
    }

    public IssueException(Throwable cause) {
        super(cause);
    }

    public IssueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
