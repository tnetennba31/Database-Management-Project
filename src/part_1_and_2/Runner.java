package part_1_and_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


/**
 * Contains all methods for accessing the databases, creating tables, 
 * and inserting and selecting data in the ways that are required by HW4
 */
public class Runner 
{
	    /**
	     * Strings that store the location of the database to connect to,
	     * as well as the user name and password
	     */
	    public static final String DB_LOCATION = "jdbc:mysql://db.cs.ship.edu:3306/csc371_08";
	    public static final String LOGIN_NAME = "csc371_08";
	    public static final String PASSWORD = "Password08";
	    

	    /*
	     * Connection variable that will be initialized in constructor 
	     */
	    protected Connection m_dbConn; 
	       
	/*
	 * Constructor that initializes the connection to the database
	 */
    public Runner() throws SQLException 
    {
        m_dbConn = DriverManager.getConnection(DB_LOCATION, LOGIN_NAME, PASSWORD);
    }

    /**
     * @return Returns true if it successfully sets up the driver.
     */
    public boolean activateJDBC()
    {
        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }

        return true;
    }
    
    /*
     * main method
     */
	public static void main(String[] args) throws Exception 
	{
		
		Runner Wwd = new Runner();

		Wwd.dropAllTables();
		Wwd.createAllTables();
		
		//Wwd.runSelectStatements();

	}
	

	/*
	 * Inserts the specified number of rows into TEST_BURDETTE
	 */
	private void addToTable(int amountToAdd) throws SQLException, InterruptedException 
	{

		String insertData;
		PreparedStatement stmt;
		
		insertData = new String("INSERT INTO TEST_BURDETTE (Col1, Col2, Col3, Col4, Col5) VALUES (?,?,?,?,?)");
	      
		stmt = m_dbConn.prepareStatement(insertData);
	    
//		stmt.setInt(1, arrayOfInts1[i]);
//		stmt.setInt(2, arrayOfInts2[i]); 
//		stmt.setString(3, arrayOfTenCharStrings[i]);
//		stmt.setString(4, arrayOfStrings[i]);
//		stmt.setDouble(5, arrayOfDoubles[i]); 
	      	      	      
		stmt.executeUpdate();
	}
	
	/*
	 * creates TEST_BURDETTE with specified columns
	 */
	public void createAllTables() throws SQLException 
	{
		
		Statement stmt;
		String insertData;
        
		stmt = m_dbConn.createStatement();
		
		//UNCOMMENT THIS AND COMMENT OUT THE ONE BELOW IT TO CREATE TABLE WITH PRIMARY KEY:
        //insertData = new String("CREATE TABLE TEST_BURDETTE (Col1 int, Col2 int, Col3 CHAR (10), Col4 VARCHAR (30), Col5 double, PRIMARY KEY (Col1))");
        insertData = new String("CREATE TABLE TEST_BURDETTE (Col1 int, Col2 int, Col3 CHAR (10), Col4 VARCHAR (30), Col5 double)");

        stmt.executeUpdate(insertData);

	}
	
	/*
	 * Drops TEST_BURDETTE table
	 */
	public void dropAllTables() throws SQLException 
	{
		
		Statement stmt;
		String insertData;
		
		stmt = m_dbConn.createStatement();
        insertData = new String("DROP TABLE TEST_BURDETTE");

        stmt.executeUpdate(insertData);			
		
	}
	
	/*
	 * Fills the 5 instance variable arrays with appropriate values so they can be pulled 
	 * to populate insert statements. This is done so that a new value does not have 
	 * to be generated to use for the insert statements on each iteration of the loop
	 */
//	public void fillRandomArrays() 
//	{
//		//fill int arrays with incremented integers 
//		for (int i = 0; i < AMOUNT_TO_REPEAT; i++) 
//		{
//			arrayOfInts1[i] = i; 
//			arrayOfInts2[i] = i; 
//		}
//		
//		Random r = new Random();
//		
//		//fill arrayOfStrings with strings of length 1-30
//		for (int i = 0; i < AMOUNT_TO_REPEAT; i++) 
//		{
//			arrayOfStrings[i] = generateRandomString(Math.abs(r.nextInt() % 29) + 1);
//		}
//		
//		//fill arrayOfTenCharStrings with strings of length 10
//		for (int i = 0; i < AMOUNT_TO_REPEAT; i++) 
//		{
//			arrayOfTenCharStrings[i] = generateRandomString(10);
//		}
//		
//		//fill arrayOfDoubles with doubles
//		for (int i = 0; i < AMOUNT_TO_REPEAT; i++) 
//		{
//			arrayOfDoubles[i] = r.nextDouble();
//		}
//		
//	}
	
	/*
	 * Returns a randomized string of the specified length 
	 */
//	public String generateRandomString(int stringLength) 
//	{
//	    int leftLimit = 97; // letter 'a'
//	    int rightLimit = 122; // letter 'z'
//	    
//	    Random random = new Random();
//	    
//	    StringBuilder buffer = new StringBuilder(stringLength);
//	    for (int i = 0; i < stringLength; i++) 
//	    {
//	        int randomLimitedInt = leftLimit + (int) 
//	          (random.nextFloat() * (rightLimit - leftLimit + 1));
//	        buffer.append((char) randomLimitedInt);
//	    }
//	    String generatedString = buffer.toString();
//	    return generatedString;
//	 }
	
	/*
	 * Runs 100 select statements starting at the specified row
	 */
//	public void runSelectStatements(int indexByHundred) throws SQLException 
//	{
//        String selectData; 
//        PreparedStatement stmt; 
//        ResultSet rs; 
//                
//        for (int i = 0; i < 100; i++) 
//        {
//        	//UNCOMMENT THIS LINE AND COMMENT OUT THE ONE BELOW IT TO SELECT FORM number2 INSTEAD OF number1
//            //selectData = new String("SELECT number1 FROM HW4_DATA WHERE number1 =" + indexByHundred + i);
//            selectData = new String("SELECT number2 FROM HW4_DATA WHERE number2 =" + indexByHundred + i);
//
//            stmt = m_dbConn.prepareStatement(selectData);
//            
//            rs = stmt.executeQuery(selectData);
//        }
//        
//	}
}


//public class Runner {
//
//	public static void main(String[] args) {
//		// populate the database appropriately
//		System.out.println("hopefully this works");
//
//	}
//
//}
