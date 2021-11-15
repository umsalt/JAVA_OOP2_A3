/*
Name & ID: Nicole Um 40112704
COMP 249
Assignment 3
Due date: November 14th Midnight
 */

/**
 * @author Nicole Um
 * @version 1.0
 * CSVDataMissingException is thrown when the CSV file is missing a data record.
 */

public class CSVDataMissingException extends InvalidException{
    public CSVDataMissingException() {
        super();
    }
    public CSVDataMissingException(String message){
        super(message);
    }
}
