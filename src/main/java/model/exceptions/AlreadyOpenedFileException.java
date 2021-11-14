package model.exceptions;

public class AlreadyOpenedFileException extends MyException{
    public AlreadyOpenedFileException(String errorMessage){
        super(errorMessage);
    }
}
