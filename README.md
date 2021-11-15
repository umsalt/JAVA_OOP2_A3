# JAVA_OOP_A3

This program converts CSV file to LATEX file. The assignment involves Exception handling and File I/O. After converting into LATEX file, the program displays the content. There are three custom exceptions: InvalidException, CSVFileInvalidException, and CSVDataMissingException.
InvalidException extends RunTimeException, and CSVFileInvalidException and CSVDataMissingException extend InvalidException.
CSVFileInvalidException is thrown when the CSV file is missing an attribute, CSVDataMissingException is thrown when a data field is missing.
When these two exceptions are thrown, an error log file is generated and displays the description of the error.
