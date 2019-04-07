# InterviewParsingCSV
Project Title:
Parsing data from CSV file and insert to a SQLite In-Memory database. 

Installing and configuring project :
Eclipse IDE - start new Maven Project
Pom.xml - installing sqlite-jdbc driver:
	<dependency>
 		<groupId>org.xerial</groupId>
 		<artifactId>sqlite-jdbc</artifactId>
 		<version>3.16.1</version>
 	</dependency>
Java classes - StringRc - converting record from a string[] to SQLite format
	       MainApp - running application

Approach : 
Parsing CVS file, checking for failed records inserting them to bad data CSV, passed records insert into the SQL database, log the results.

To reach the final result of my app i had to go through different steps:
1)Open CSV file by String class and reading the data from the CSV file using Java BufferedReader class.
2)Using PrintWriter class to write Log file in txt format and to write failed records file to a csv format.
3)Creating connection to database SQLite, drop the previous created table if it exists, create a new SQLite table,
4)Making BuffedReader verification if it is not null then split on the comma outside quotes using regex.
5)Writing bad records to the bad data csv file and count them for the Log file,
  passed records are also count and each row elements with commas will be double quoted by StringRc class and inserted to a new SQLite database 
6)Close all connections.

DDL:
CREATE TABLE interviewCSVToSqlite (
    A TEXT NOT NULL,
    B TEXT NOT NULL,
    C TEXT NOT NULL,
    D TEXT NOT NULL,
    E TEXT NOT NULL,
    F TEXT NOT NULL,
    G TEXT NOT NULL,
    H INT  NOT NULL,
    I INT  NOT NULL,
    J TEXT NOT NULL
);
