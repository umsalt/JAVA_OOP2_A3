

/**
 * @author Nicole Um
 * @version 1.0
 * CSVFileInvalidException extends InvalidException and is thrown when the CSV file is missing an attribute.
 */

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CSVFileInvalidException extends InvalidException{

    /**
     *
     */
    public CSVFileInvalidException() {
        super();
    }
    public CSVFileInvalidException(String message){
        super(message);
    }

}
