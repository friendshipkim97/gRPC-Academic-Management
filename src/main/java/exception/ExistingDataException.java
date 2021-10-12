package exception;

public class ExistingDataException extends Exception{

    public ExistingDataException(String errorMessage) {
        super(errorMessage);
    }
}
