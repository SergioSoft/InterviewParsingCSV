package parsingCsvSql;
import java.io.*;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class MainApp {

    
    @SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
    	
    	StringRc rec = new StringRc();
    	Date date = new Date();
    	
    	int passedRecords = 0;
        int failedRecords = 0;
        String[] csvRow;
        String insertIntoTable;
    	final String driverName = "org.sqlite.JDBC";
        final String connectionString = "jdbc:sqlite:InterviewCSVToSqlite.db";
    	Connection connection = null;
    	BufferedReader reader = null;
    	FileReader fileReader = null;
    	String csvLine = null;
    	String csvName = "D:\\Projects_Java\\InterviewParsingCSV\\InterviewCSV.csv";   
    	
    	SimpleDateFormat DateFormatStamp = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
    	DateFormatStamp.setTimeZone(TimeZone.getDefault());


        PrintWriter log = new PrintWriter(new FileWriter("Log-" + DateFormatStamp.format(date) + ".txt"));
        PrintWriter badRecords = new PrintWriter(new FileWriter("bad-data-" + DateFormatStamp.format(date) + ".csv"));
    	
        try {
             // read csv file           
            reader = new BufferedReader(fileReader = new FileReader(csvName));
            System.out.println("CSV File open successful!");
            
            if (reader.readLine() == null) {
            	reader.close();
                fileReader.close();
                return;
            }          
             // create connection to database
            Class.forName(driverName);
            connection = DriverManager.getConnection(connectionString);
            System.out.println("Connection to SQLite database has been established....");
            Statement statement = connection.createStatement();

            statement.executeUpdate("drop table if exists InterviewCSVToSqlite");
            statement.executeUpdate(
                    "CREATE TABLE interviewCSVToSqlite(A TEXT NOT NULL, B TEXT NOT NULL,"
                    + "C TEXT NOT NULL, D TEXT NOT NULL, E TEXT NOT NULL, F TEXT NOT NULL,"
                    + "G TEXT NOT NULL, H INT NOT NULL, I INT NOT NULL, J TEXT NOT NULL);");
                   
            while ((csvLine = reader.readLine()) != null) {
            	
                // Splitting on comma outside quotes
            	String otherThanQuote = " [^\"] ";
                String quotedString = String.format(" \" %s* \" ", otherThanQuote);
                String regex = String.format("(?x),(?=(?:%s*%s)*%s*$)",otherThanQuote, quotedString, otherThanQuote);
                csvRow = csvLine.split(regex , -1); 
                 if (csvRow.length != 10 || csvLine.contains(",,")) {
                    // write bad record to the bad data csv file
                    badRecords.append(csvLine + "\n");
                    failedRecords++;
                    
                 	} else {
                    passedRecords++;
                    insertIntoTable = String.format("INSERT INTO InterviewCSVToSqlite values(%s)",rec.tableRec(csvRow));
                    statement.execute(insertIntoTable);                                
                 			}               
            		}
            System.out.println("Data into InterviewCSVToSqlite database inserted");
            
            // write logs
            log.append(String.format("# Number total records  – " + (failedRecords + passedRecords)) + "\n");         
            log.append(String.format("# Number passed records – " + passedRecords + "\n"));
            log.append(String.format("# Number failed records – " + failedRecords + "\n"));

            log.close();
            badRecords.close();
            reader.close();
            fileReader.close();
            

            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                try {
                    if (connection != null)
                        connection.close();
                    	System.out.println("Connection closed");
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        }
    }