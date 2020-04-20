package part_1_and_2;

import java.sql.*;


/**
 * Contains all methods for accessing the databases, creating tables,
 * and inserting and selecting data in the ways that are required by HW4
 */
public class Runner {
  /**
   * main method
   */
  public static void main(String[] args) throws Exception {

    Runner runner = new Runner();

    runner.dropAllTables();
    runner.createAllTables();

    runner.insertEverythingIntoTables();

    //use for testing
//    runner.showTables();
//    runner.printAllRows("TEST_BURDETTE");

  }

  /**
   * Strings that store the location of the database to connect to,
   * as well as the user name and password
   */
  public static final String DB_LOCATION = "jdbc:mysql://db.cs.ship.edu:3306/csc371_08";
  public static final String LOGIN_NAME = "csc371_08";
  public static final String PASSWORD = "Password08";


  /**
   * Connection variable that will be initialized in constructor
   */
  protected Connection m_dbConn;

  /*
   * Constructor that initializes the connection to the database
   */
  public Runner() throws SQLException {
    m_dbConn = DriverManager.getConnection(DB_LOCATION, LOGIN_NAME, PASSWORD);
  }

  /**
   * @return Returns true if it successfully sets up the driver.
   */
  public boolean activateJDBC() {
    try {
      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }

    return true;
  }

  /**
   * Inserts the initial rows of data into all the tables
   */
  private void insertEverythingIntoTables() throws SQLException {

    Statement stmt;
    String insertData;

    stmt = m_dbConn.createStatement();

    String[] insert_statements =
            {"INSERT INTO PERSON VALUES ('DefaultLogin', 'password123', 'imbasic@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ManLogin', 'helloMadeline', 'imalsobasic@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ModLogin', 'hiAdamWeissinger', 'morebasic@gmail.com')"
            };

    for (int i = 0; i < insert_statements.length; i++) {
      insertData = new String(insert_statements[i]);
      stmt.executeUpdate(insertData);
      System.out.println("inserted stuff " + i);
    }

  }

  /**
   * creates all the tables in the database with specified columns
   */
  public void createAllTables() throws SQLException {

    Statement stmt;
    String insertData;

    stmt = m_dbConn.createStatement();

    String[] table_statements =
            {"CREATE TABLE PERSON (Login VARCHAR(16) NOT NULL, Pwd VARCHAR(16) NOT NULL, Email VARCHAR(320) NOT NULL, PRIMARY KEY (Login))",
                    "CREATE TABLE MANAGER (Manager_Login VARCHAR(16) NOT NULL, Pe_Login VARCHAR(16) NOT NULL, PRIMARY KEY (Manager_Login), FOREIGN KEY (Pe_Login) REFERENCES PERSON(Login))",
                    "CREATE TABLE MODERATOR (Moderator_Login VARCHAR(16) NOT NULL, Man_Login VARCHAR(16), Pe_Login VARCHAR(16) NOT NULL, PRIMARY KEY (Moderator_Login), FOREIGN KEY (Man_Login) REFERENCES MANAGER(Manager_Login), FOREIGN KEY (Pe_Login) REFERENCES PERSON(Login))",
                    "CREATE TABLE PLAYER (Player_Login VARCHAR(16) NOT NULL, Mod_Login VARCHAR(16), Man_Login VARCHAR(16), Pe_Login VARCHAR(16) NOT NULL, PRIMARY KEY (Player_Login), FOREIGN KEY (Mod_Login) REFERENCES MODERATOR(Moderator_Login), FOREIGN KEY (Man_Login) REFERENCES MANAGER(Manager_Login), FOREIGN KEY (Pe_Login) REFERENCES PERSON(Login))",
                    "CREATE TABLE LOCATION (L_ID INT NOT NULL, Type INT NOT NULL, Size INT NOT NULL, PRIMARY KEY (L_ID))",
                    "CREATE TABLE P_CHARACTER (C_Name VARCHAR(32) NOT NULL, Strength INT NOT NULL,Max_HP INT NOT NULL, Current_HP INT NOT NULL, Stamina INT NOT NULL, C_Location INT NOT NULL, P_Login VARCHAR(16) NOT NULL, PRIMARY KEY (C_Name), FOREIGN KEY (C_Location) REFERENCES LOCATION(L_ID), FOREIGN KEY (P_Login) REFERENCES PLAYER(Player_Login))",
                    "CREATE TABLE LOCATION_EXITS_TO_LOCATION (L1_ID INT NOT NULL, L2_ID INT NOT NULL, PRIMARY KEY (L1_ID, L2_ID), FOREIGN KEY (L1_ID) REFERENCES LOCATION(L_ID), FOREIGN KEY (L2_ID) REFERENCES LOCATION(L_ID))",
                    "CREATE TABLE CREATURE (ID INT NOT NULL, Stamina INT NOT NULL, Strength INT NOT NULL, Damage_Protection INT NOT NULL, Current_HP INT NOT NULL, Max_HP INT NOT NULL, L_ID INT NOT NULL, PRIMARY KEY (ID), FOREIGN KEY (L_ID) REFERENCES LOCATION(L_ID))",
                    "CREATE TABLE ABILITY (ID INT NOT NULL, Time_to_Execute INT NOT NULL, Effect_Amount INT NOT NULL, Type INT NOT NULL, Target_Stat INT NOT NULL, Amount_of_Times INT NOT NULL, Rate INT NOT NULL, Creature_ID INT, PRIMARY KEY (ID), FOREIGN KEY (Creature_ID) REFERENCES CREATURE(ID))",
                    "CREATE TABLE CREATURE_HAS_POSSIBLE_AREAS (C_ID INT NOT NULL, Type INT NOT NULL, PRIMARY KEY (C_ID, Type), FOREIGN KEY (C_ID) REFERENCES CREATURE(ID))",
                    "CREATE TABLE CREATURE_LIKES_HATES_PLAYER (C_ID INT NOT NULL, P_ID VARCHAR(16) NOT NULL, Emotion INT NOT NULL, PRIMARY KEY (C_ID, P_ID), FOREIGN KEY (C_ID) REFERENCES CREATURE(ID), FOREIGN KEY (P_ID) REFERENCES PLAYER(Player_Login))",
                    "CREATE TABLE CREATURE_LIKES_HATES_CREATURE (C1_ID INT NOT NULL, C2_ID INT NOT NULL, Emotion INT NOT NULL, PRIMARY KEY (C1_ID, C2_ID), FOREIGN KEY (C1_ID) REFERENCES CREATURE(ID), FOREIGN KEY (C2_ID) REFERENCES CREATURE(ID))",
                    "CREATE TABLE ITEM (ID INT NOT NULL, Volume INT NOT NULL, Weight INT NOT NULL, L_ID INT, O_Name VARCHAR(32), W_Name VARCHAR(32), PRIMARY KEY (ID), FOREIGN KEY (L_ID) REFERENCES LOCATION(L_ID), FOREIGN KEY (O_Name) REFERENCES P_CHARACTER(C_Name), FOREIGN KEY (W_Name) REFERENCES P_CHARACTER(C_Name))",
                    "CREATE TABLE CONTAINER (Con_ID INT NOT NULL, Volume_Limit INT NOT NULL, Weight_Limit INT NOT NULL, I_ID INT NOT NULL, PRIMARY KEY (Con_ID), FOREIGN KEY (I_ID) REFERENCES ITEM(ID))",
                    "ALTER TABLE ITEM ADD COLUMN Con_ID INT",
                    "ALTER TABLE ITEM ADD CONSTRAINT FOREIGN KEY (Con_ID) REFERENCES CONTAINER(Con_ID)",
                    "CREATE TABLE ARMOR (A_ID INT NOT NULL, Place INT NOT NULL, Protection_Amount INT NOT NULL, I_ID INT NOT NULL, PRIMARY KEY (A_ID), FOREIGN KEY (I_ID) REFERENCES ITEM(ID))",
                    "CREATE TABLE WEAPON (W_ID INT NOT NULL, Ability_ID INT, I_ID INT NOT NULL, PRIMARY KEY (W_ID), FOREIGN KEY (Ability_ID) REFERENCES ABILITY(ID), FOREIGN KEY (I_ID) REFERENCES ITEM(ID))",
                    "CREATE TABLE GENERIC_ITEM (GI_ID INT NOT NULL, I_ID INT NOT NULL, PRIMARY KEY (GI_ID), FOREIGN KEY (I_ID) REFERENCES ITEM(ID))"
            };

    for (int i = 0; i < table_statements.length; i++) {
      insertData = new String(table_statements[i]);
      stmt.executeUpdate(insertData);
      System.out.println("created table " + i);
    }

  }

  /**
   * Drops the tables
   */
  public void dropAllTables() throws SQLException {

    Statement stmt;
    String insertData;

    stmt = m_dbConn.createStatement();

    String[] table_statements =
            {"DROP TABLE IF EXISTS PERSON",
                    "DROP TABLE IF EXISTS MANAGER",
                    "DROP TABLE IF EXISTS MODERATOR",
                    "DROP TABLE IF EXISTS PLAYER",
                    "DROP TABLE IF EXISTS LOCATION",
                    "DROP TABLE IF EXISTS P_CHARACTER",
                    "DROP TABLE IF EXISTS LOCATION_EXITS_TO_LOCATION",
                    "DROP TABLE IF EXISTS CREATURE",
                    "DROP TABLE IF EXISTS ABILITY",
                    "DROP TABLE IF EXISTS CREATURE_HAS_POSSIBLE_AREAS",
                    "DROP TABLE IF EXISTS CREATURE_LIKES_HATES_PLAYER",
                    "DROP TABLE IF EXISTS CREATURE_LIKES_HATES_CREATURE",
                    "DROP TABLE IF EXISTS ITEM",
                    "DROP TABLE IF EXISTS CONTAINER",
                    "DROP TABLE IF EXISTS ARMOR",
                    "DROP TABLE IF EXISTS WEAPON",
                    "DROP TABLE IF EXISTS GENERIC_ITEM"
            };

    for (int i = table_statements.length - 1; i >= 0; i--) {
      insertData = new String(table_statements[i]);
      stmt.executeUpdate(insertData);
      System.out.println("yee");
    }

  }


  /**
   * runs show tables command
   */
  private void showTables() throws SQLException {
    String s = new String("show tables");
    PreparedStatement stmt = m_dbConn.prepareStatement(s);
    ResultSet rs = stmt.executeQuery(s);

    while (rs.next()) {
      System.out.print(rs.getString(1));
      System.out.println();
    }

    System.out.println("\n");
  }


  /**
   * runs SELECT * FROM table_name
   */
  private void printAllRows(String tableName) throws SQLException {
    String selectData = new String("SELECT * FROM " + tableName);
    PreparedStatement stmt = m_dbConn.prepareStatement(selectData);
    ResultSet rs = stmt.executeQuery(selectData);

    ResultSetMetaData rsmd = rs.getMetaData();
    int columnsNumber = rsmd.getColumnCount();

    while (rs.next()) {
      for (int i = 1; i < columnsNumber; i++)
        System.out.print(rs.getString(i) + " ");
      System.out.println();
    }

  }

}

