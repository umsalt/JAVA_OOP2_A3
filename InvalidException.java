/*
Name & ID: Nicole Um 40112704
COMP 249
Assignment 3
Due date: November 14th Midnight
 */

/**
 * @author Nicole Um
 * @version 1.0
 * InvalidException extends RuntimeException and is a parent exception class of CSVFileInvalidException and CSVDataMissingException.
 */

public class InvalidException extends RuntimeException
{
    /**
     * InvalidException() is a default constructor of this exception class.
     * displays generic error message.
     */
    public InvalidException() {
        System.err.println("Error: Input row cannot be parsed due to missing information");
    }

    /**
     * InvalidException(String message) is a parameterized constructor of this exception class that takes a string message to be displayed.
     * @param message will be displayed from the parent's exception method - RuntimeException
     */
    public InvalidException(String message){
        super(message);
    }
}
