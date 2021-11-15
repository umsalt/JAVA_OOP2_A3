

/**
 * @author Nicole Um
 * @version 1.0
 * @use This program converts CSV file to LATEX file. The assignment involves Exception handling and File I/O. After converting into LATEX file, the program displays the content.
 * There are three custom exceptions: InvalidException, CSVFileInvalidException, and CSVDataMissingException.
 * InvalidException extends RunTimeException, and CSVFileInvalidException and CSVDataMissingException extend InvalidException.
 * CSVFileInvalidException is thrown when the CSV file is missing an attribute, CSVDataMissingException is thrown when a data record is missing a data.
 * When these two exceptions are thrown, an error log file is generated and displays the description of the error.
 */

import java.io.*;
import java.util.Scanner;

public class Main {

    /**
     * @throws IOException is thrown when there is no file under the file name given by the user when using the displayFile(fileName) method.
     */
    public static void main(String[] args) throws IOException {


        System.out.println("*******************************************************************\n"+
                "                  Welcome to the CSV2LATEX Program!                " +
                "\n*******************************************************************");
        System.out.println("Please enter CSV file path that you would like to convert into LATEX file.");

        Scanner keyIn = new Scanner(System.in);

        String csvFile2Convert = keyIn.next();

        processFilesForValidation(csvFile2Convert);
        processCSV2LATEX(csvFile2Convert);



        System.out.println("Please enter the name of the output to display.");

        String output2Display = keyIn.next();
        displayFile(output2Display);
        System.out.println("\nThank you for using CSV2LATEX Program! Bye!");

    }

    private static void processCSV2LATEX(String someFile){
        PrintWriter out = null;
        String outputFile = null;
        try(Scanner in = new Scanner(new FileInputStream(someFile))){
            try {
                outputFile = someFile.replace(".csv",".tex");
                out = new PrintWriter(new FileOutputStream(outputFile));
            }
            catch(FileNotFoundException e){
                System.err.println("[ERROR]: Could not open/create the output file " +someFile + "."
                        + "\nPlease restart the program.");
                //delete the created output file
                out.close();//close opened input files
                System.out.println("Terminating the program...");
                System.exit(0); //terminating the program.
            }

            //writing latex file
            out.println("\\documentclass{article}\n" +
                    "\\usepackage[T1]{fontenc}\n" +
                    "\\usepackage{array}\n" +
                    "\\usepackage{booktabs}");
            out.println("\\begin{document}[]");
            out.print("\\begin{tabular}{|");

            //storing the first line of csv line as a title
            String title = in.nextLine().replace(",","");

            //storing the second line of csv as an attribute array
            String [] attribute = (in.nextLine()).split(",");

            //table size declaration in latex file
            for (int i =0; i< attribute.length; i++){
                out.print("l|");
            }
            out.println("}" +
                    "\n\\toprule");

            //writing attribute line in .tex file
            for (int j=0; j< attribute.length; j++) {
                out.print(attribute[j]);//separating elements
                if (j!= attribute.length-1){
                    out.print(" & ");
                }
            }

            while(in.hasNextLine()) {
                String line = in.nextLine();
                if (!(line.contains(",,"))){
                    String body [] = (line.split(","));
                    out.println(" \\\\"+ " \\midrule");
                    for (int i =0; i< body.length; i++){
                        out.print(body[i]);
                        if (i!=body.length-1){
                            out.print(" & ");
                        }
                    }
                }
            }

            //end of latex table
            out.println("\\\\" + " \\bottomrule");
            out.println("\\end{tabular}" +
                    "\n\\caption{" + title + "}" +
                    "\n\\label{" + someFile + "}" +
                    "\n\\end{document}");

        }
        catch (FileNotFoundException e){
            System.err.println("Could not open input file " + someFile + "." +
                    "\nPlease check if file exists! Program will terminate after closing any opened files.");
            System.out.println("Terminating the program...");
            System.exit(0);
        }
        finally {
            out.close();
        }
    }


    private static void processFilesForValidation(String someFile) {

        try {
            fileInvalid(someFile);
        } catch (FileNotFoundException | CSVFileInvalidException e) {
            System.out.println(e.getMessage());
            System.out.println("Terminating the program...");
            System.exit(0);
        }
        try {
            dataMissing(someFile);
        } catch (FileNotFoundException | CSVDataMissingException e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * fileInvalid(String someFile) is a void method.
     * @param someFile passed from the main method by the user.
     * @throws FileNotFoundException is thrown when there is no file under the file name given by the user.
     * @throws CSVFileInvalidException is thrown when the CSV file is missing an attribute.
     */
    private static void fileInvalid(String someFile) throws FileNotFoundException, CSVFileInvalidException {
        Scanner in = new Scanner(new FileInputStream(someFile));
        //READS THE TITLE - THE FIRST LINE
        in.nextLine();
        //READS THE SECOND LINE
        String line = in.nextLine();
        if (line.contains(",,")) {
            PrintWriter out = new PrintWriter(new FileOutputStream(someFile.replace(".csv", "") + "log.txt", true));
            line = line.replace(",,", ",***,"); //replacing ,, with ,***, for the log file.
//          System.out.println("File " + someFile + " is invalid: attribute is missing.\n" + line + "\nFile is not converted to LATEX."); //user message
            out.println("File " + someFile + " is invalid. " + "Attribute is missing.");
            in.close();
            out.close();
            throw new CSVFileInvalidException("File " + someFile + " is invalid: attribute is missing.\n" + line + "\nFile is not converted to LATEX.");
            }
        }


    /**
     * dataMissing(String someFile) is a void method.
     * @param someFile passed from the main method by the user.
     * @throws FileNotFoundException is thrown when there is no file under the file name given by the user.
     * @throws CSVDataMissingException is thrown when the CSV file is missing a data record.
     */
    private static void dataMissing(String someFile) throws FileNotFoundException, CSVDataMissingException {
        Scanner in = new Scanner(new FileInputStream(someFile));
        //read the first two lines - title and attribute
        in.nextLine();
        String line = in.nextLine();
        String [] attributeValues = line.split(",");

        //creating log file
        PrintWriter out = new PrintWriter(new FileOutputStream(someFile.replace(".csv", "") + "ErrorLog.txt", true));
        //read the records and document missing data records in the log file
        int countLine = 0;
        while(in.hasNextLine()) {
            line = in.nextLine();
            countLine++;
            int missingDataNum =0;
            if (line.contains(",,")){
                line = line.replace(",,",",***,"); //replacing ,, with ,***, for the log file.
                String dataValues [] = line.split(",");
                for (int i=0; i< attributeValues.length; i++){
                    if (dataValues[i].equals("***")) {
                        missingDataNum = i;
                    }
                }
                out.println("File " + someFile + " line " + countLine + ".\n" + line + "\nMissing: " + attributeValues[missingDataNum]); //log file
                in.close();
                out.close();
                throw new CSVDataMissingException("In file" + someFile + " line " + countLine + " not converted to LATEX : missing data.");
            }
        }
    }

    /**
     * displayFile(String someFile) is a void method.
     * @param someFile passed from the main method by the user.
     * @throws IOException is thrown when there is no file under the file name given by the user.
     */
    private static void displayFile(String someFile) throws IOException {
        Scanner keyIn = new Scanner(System.in);

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(someFile));
            String line;
            while((line = br.readLine())!= null) {
                System.out.println(line);

            }
        }
        catch (FileNotFoundException e) {
            System.out.println("[ERROR]: Entered file name cannot not be found. " +
                    "\nPlease enter the name of the output to display. ");
            someFile = keyIn.next();
            try{
                br = new BufferedReader(new FileReader(someFile));
                String line;
                while((line = br.readLine())!= null) {
                    System.out.println(line);
                }
            }
            catch (FileNotFoundException e2){
                System.out.println("[ERROR]: Entered file name cannot be found." +
                        "\nTerminating the program...");
                System.exit(0);
            }
            catch (IOException e3){
                e3.printStackTrace();
            }
    }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            br.close();
        }
    }
}

