package exception;

public class DuplicateDataException extends Exception{

    public DuplicateDataException(String errorMessage) {
        super(errorMessage);
    }
}
