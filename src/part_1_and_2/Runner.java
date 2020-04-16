package part_1_and_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
		
		Runner runner = new Runner();

		runner.dropAllTables();   
		runner.createAllTables(); 
		
		runner.insertEverythingIntoTables();
		
		//use for testing
		runner.showTables();
		runner.printAllRows("TEST_BURDETTE");

	}
	

	/*
	 * Inserts the specified number of rows into TEST_BURDETTE
	 */
	private void insertEverythingIntoTables() throws SQLException, InterruptedException 
	{

		String insertData;
		PreparedStatement stmt;
		
		//CHANGE THIS TO CORRECT ONES:
		insertData = new String("INSERT INTO TEST_BURDETTE (Col1, Col2, Col3, Col4, Col5) VALUES (?,?,?,?,?)");
	      
		stmt = m_dbConn.prepareStatement(insertData);
	    
		stmt.setInt(1, 100);
		stmt.setInt(2, 200); 
		stmt.setString(3, "string 1");
		stmt.setString(4, "string 2");
		stmt.setDouble(5, 100.999); 
	      	      	      
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
		
        insertData = new String("CREATE TABLE TEST_BURDETTE (Col1 int, Col2 int, Col3 CHAR (10), Col4 VARCHAR (30), Col5 double, PRIMARY KEY (Col1))");

        stmt.executeUpdate(insertData);

	}
	
	/*
	 * Drops the tables
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
	 * runs show tables command
	 */
	private void showTables() throws SQLException 
	{
        String s = new String("show tables");
        PreparedStatement stmt = m_dbConn.prepareStatement(s);
        ResultSet rs = stmt.executeQuery(s);
        
        while (rs.next())
        {
            System.out.print(rs.getString(1));
            System.out.println();       	
        }		
        
        System.out.println("\n");       	
	}
	

	/*
	 * runs SELECT * FROM table_name 
	 */
	private void printAllRows(String tableName) throws SQLException {
        String selectData = new String("SELECT * FROM " + tableName);
        PreparedStatement stmt = m_dbConn.prepareStatement(selectData);
        ResultSet rs = stmt.executeQuery(selectData);
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        while (rs.next()) {
            for(int i = 1; i < columnsNumber; i++)
                System.out.print(rs.getString(i) + " ");
            System.out.println();
        }

	}

}

