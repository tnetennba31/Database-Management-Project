package part_1_and_2;

import java.sql.*;

import part_3.DisplayThree;
import part_3.DisplayThreeSQLHandler;
import part_3.Display_4;


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
//    runner.dropAllTables();
//    runner.createAllTables();

//    runner.insertEverythingIntoTables();
//	  Runner.createStoredProcedures4();
//	  DisplayThreeSQLHandler.setConnection(m_dbConn);
//	  DisplayThreeSQLHandler.setStoredProcedures();
//	  DisplayThree display3 = DisplayThree.getInstance();
	  new Display_4(m_dbConn);
	  


    //use for testing
//    runner.showTables();
//    runner.printAllRows("PERSON");

  }

  public static void createStoredProcedures4() throws SQLException {
    Statement stmt = m_dbConn.createStatement();
    String storedProcedure1 = new String("CREATE PROCEDURE get_characters (IN player_login VARCHAR(16)) BEGIN SELECT C_Name FROM P_CHARACTER WHERE P_Login = player_login; END");
    stmt.executeUpdate(storedProcedure1);
    String storedProcedure2 = new String("CREATE PROCEDURE get_items_owned (IN cname VARCHAR(32)) BEGIN SELECT ID FROM ITEM WHERE O_Name = cname AND W_Name IS NULL; END");
    stmt.executeUpdate(storedProcedure2);
    String storedProcedure3 = new String("CREATE PROCEDURE get_items_worn (IN cname VARCHAR(32)) BEGIN SELECT ID FROM ITEM WHERE W_Name = cname; END");
    stmt.executeUpdate(storedProcedure3);
    String storedProcedure4 = new String("CREATE PROCEDURE switch_item_to_worn (IN id INT) BEGIN UPDATE ITEM SET W_Name = O_Name WHERE ID = id; END");
    stmt.executeUpdate(storedProcedure4);
    String storedProcedure5 = new String("CREATE PROCEDURE switch_item_to_used (IN id INT) BEGIN UPDATE ITEM SET W_Name = NULL WHERE ID = id; END");
    stmt.executeUpdate(storedProcedure5);
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
  protected static Connection m_dbConn;

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
                    "ALTER TABLE ITEM DROP FOREIGN KEY ITEM_ibfk_4",
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
   * Inserts the initial rows of data into all the tables
   */
  private void insertEverythingIntoTables() throws SQLException {

    Statement stmt;
    String insertData;

    stmt = m_dbConn.createStatement();

    String[] insert_statements =
            {
                    // PERSON
                    "INSERT INTO PERSON VALUES ('ManLogin', 'helloMadeline', 'imalsobasic@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ManLogin2', 'helloMadelin', 'imalsobasic2@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ManLogin3', 'helloMadeli', 'imalsobasic3@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ManLogin4', 'helloMadel', 'imalsobasic4@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ManLogin5', 'helloMade', 'imalsobasic5@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ModLogin', 'hiAdamWeissinger', 'morebasic@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ModLogin2', 'hiAdamWeissinge', 'morebasic2@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ModLogin3', 'hiAdamWeissing', 'morebasic3@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ModLogin4', 'hiAdamWeissin', 'morebasic4@gmail.com')",
                    "INSERT INTO PERSON VALUES ('ModLogin5', 'hiAdamWeissi', 'morebasic5@gmail.com')",
                    "INSERT INTO PERSON VALUES ('DefaultLogin', 'password123', 'imbasic@gmail.com')",
                    "INSERT INTO PERSON VALUES ('DefaultLogin2', 'password456', 'imbasic2@gmail.com')",
                    "INSERT INTO PERSON VALUES ('DefaultLogin3', 'password789', 'imbasic3@gmail.com')",
                    "INSERT INTO PERSON VALUES ('DefaultLogin4', 'password321', 'imbasic4@gmail.com')",
                    "INSERT INTO PERSON VALUES ('DefaultLogin5', 'password654', 'imbasic5@gmail.com')",
                    // MANAGER
                    "INSERT INTO MANAGER VALUES ('MrManager', (SELECT P.Login FROM PERSON P WHERE P.Login = 'ManLogin'))",
                    "INSERT INTO MANAGER VALUES ('MrManager2', (SELECT P.Login FROM PERSON P WHERE P.Login = 'ManLogin2'))",
                    "INSERT INTO MANAGER VALUES ('MrManager3', (SELECT P.Login FROM PERSON P WHERE P.Login = 'ManLogin3'))",
                    "INSERT INTO MANAGER VALUES ('MrManager4', (SELECT P.Login FROM PERSON P WHERE P.Login = 'ManLogin4'))",
                    "INSERT INTO MANAGER VALUES ('MrManager5', (SELECT P.Login FROM PERSON P WHERE P.Login = 'ManLogin5'))",
                    // MODERATOR
                    "INSERT INTO MODERATOR VALUES ('MrModerator', NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'ModLogin'))",
                    "INSERT INTO MODERATOR VALUES ('MrModerator2', NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'ModLogin2'))",
                    "INSERT INTO MODERATOR VALUES ('MrModerator3', NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'ModLogin3'))",
                    "INSERT INTO MODERATOR VALUES ('MrModerator4', NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'ModLogin4'))",
                    "INSERT INTO MODERATOR VALUES ('MrModerator5', NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'ModLogin5'))",
                    // PLAYER
                    "INSERT INTO PLAYER VALUES ('Noobmaster', NULL, NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'DefaultLogin'))",
                    "INSERT INTO PLAYER VALUES ('Noobmaster2', NULL, NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'DefaultLogin2'))",
                    "INSERT INTO PLAYER VALUES ('Noobmaster3', NULL, NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'DefaultLogin3'))",
                    "INSERT INTO PLAYER VALUES ('Noobmaster4', NULL, NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'DefaultLogin4'))",
                    "INSERT INTO PLAYER VALUES ('Noobmaster5', NULL, NULL, (SELECT P.Login FROM PERSON P WHERE P.Login = 'DefaultLogin5'))",
                    // LOCATION
                    "INSERT INTO LOCATION VALUES (1,0,200)",
                    "INSERT INTO LOCATION VALUES (2,1,350)",
                    "INSERT INTO LOCATION VALUES (3,2,201)",
                    "INSERT INTO LOCATION VALUES (4,0,351)",
                    "INSERT INTO LOCATION VALUES (5,1,202)",
                    "INSERT INTO LOCATION VALUES (6,2,352)",
                    "INSERT INTO LOCATION VALUES (7,0,203)",
                    "INSERT INTO LOCATION VALUES (8,1,353)",
                    "INSERT INTO LOCATION VALUES (9,2,204)",
                    "INSERT INTO LOCATION VALUES (10,0,354)",
                    // P_CHARACTER
                    "INSERT INTO P_CHARACTER VALUES ('Raffy', 10, 20, 20, 10, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster'))",
                    "INSERT INTO P_CHARACTER VALUES ('Limmy', 11, 21, 21, 11, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster2'))",
                    "INSERT INTO P_CHARACTER VALUES ('BillCrouse', 12, 22, 22, 12, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster3'))",
                    "INSERT INTO P_CHARACTER VALUES ('Richmond', 13, 23, 23, 13, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster4'))",
                    "INSERT INTO P_CHARACTER VALUES ('Leeroy Jenkins', 14, 24, 24, 14, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster5'))",
                    "INSERT INTO P_CHARACTER VALUES ('Roy', 17, 20, 22, 19, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 6), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster5'))",
                    "INSERT INTO P_CHARACTER VALUES ('Moss', 16, 24, 20, 18, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 7), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster5'))",
                    "INSERT INTO P_CHARACTER VALUES ('Jen', 12, 21, 28, 13, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 8), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster5'))",
                    "INSERT INTO P_CHARACTER VALUES ('Douglas', 11, 50, 42, 31, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster5'))",
                    // LOCATION_EXITS_TO_LOCATION
                    "INSERT INTO LOCATION_EXITS_TO_LOCATION VALUES ((SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1), (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2))",
                    "INSERT INTO LOCATION_EXITS_TO_LOCATION VALUES ((SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3), (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4))",
                    "INSERT INTO LOCATION_EXITS_TO_LOCATION VALUES ((SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5), (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 6))",
                    "INSERT INTO LOCATION_EXITS_TO_LOCATION VALUES ((SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 7), (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 8))",
                    "INSERT INTO LOCATION_EXITS_TO_LOCATION VALUES ((SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 9), (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 10))",
                    // CREATURE
                    "INSERT INTO CREATURE VALUES (1,150,4,8,15,16, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1))",
                    "INSERT INTO CREATURE VALUES (2,175,23,42,25,37, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2))",
                    "INSERT INTO CREATURE VALUES (3,151,5,9,16,17, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3))",
                    "INSERT INTO CREATURE VALUES (4,176,24,43,26,38, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4))",
                    "INSERT INTO CREATURE VALUES (5,152,6,10,17,18, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5))",
                    "INSERT INTO CREATURE VALUES (6,177,25,44,27,39, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 6))",
                    "INSERT INTO CREATURE VALUES (7,153,7,11,18,19, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 7))",
                    "INSERT INTO CREATURE VALUES (8,178,26,45,28,40, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 8))",
                    "INSERT INTO CREATURE VALUES (9,154,8,12,19,20, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 9))",
                    "INSERT INTO CREATURE VALUES (10,179,27,46,29,41, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 10))",
                    
                    "INSERT INTO CREATURE VALUES (11,150,4,8,15,16, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1))",
                    "INSERT INTO CREATURE VALUES (12,175,23,42,25,37, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2))",
                    "INSERT INTO CREATURE VALUES (13,151,5,9,16,17, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3))",
                    "INSERT INTO CREATURE VALUES (14,176,24,43,26,38, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4))",
                    "INSERT INTO CREATURE VALUES (15,152,6,10,17,18, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5))",
                    
                    "INSERT INTO CREATURE VALUES (16,150,4,8,15,16, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1))",
                    "INSERT INTO CREATURE VALUES (17,175,23,42,25,37, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2))",
                    "INSERT INTO CREATURE VALUES (18,151,5,9,16,17, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3))",
                    "INSERT INTO CREATURE VALUES (19,176,24,43,26,38, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4))",
                    "INSERT INTO CREATURE VALUES (20,152,6,10,17,18, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5))",
                    
                    "INSERT INTO CREATURE VALUES (21,150,4,8,15,16, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1))",
                    "INSERT INTO CREATURE VALUES (22,175,23,42,25,37, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2))",
                    "INSERT INTO CREATURE VALUES (23,151,5,9,16,17, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3))",
                    "INSERT INTO CREATURE VALUES (24,176,24,43,26,38, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4))",
                    "INSERT INTO CREATURE VALUES (25,152,6,10,17,18, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5))",
                    
                    "INSERT INTO CREATURE VALUES (26,150,4,8,15,16, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1))",
                    "INSERT INTO CREATURE VALUES (27,175,23,42,25,37, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2))",
                    "INSERT INTO CREATURE VALUES (28,151,5,9,16,17, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3))",
                    "INSERT INTO CREATURE VALUES (29,176,24,43,26,38, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4))",
                    "INSERT INTO CREATURE VALUES (30,152,6,10,17,18, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5))",
                    
                    "INSERT INTO CREATURE VALUES (31,150,4,8,15,16, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1))",
                    "INSERT INTO CREATURE VALUES (32,175,23,42,25,37, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2))",
                    "INSERT INTO CREATURE VALUES (33,151,5,9,16,17, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3))",
                    "INSERT INTO CREATURE VALUES (34,176,24,43,26,38, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4))",
                    "INSERT INTO CREATURE VALUES (35,152,6,10,17,18, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5))",
                    
                    "INSERT INTO CREATURE VALUES (36,150,4,8,15,16, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 1))",
                    "INSERT INTO CREATURE VALUES (37,175,23,42,25,37, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 2))",
                    "INSERT INTO CREATURE VALUES (38,151,5,9,16,17, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 3))",
                    "INSERT INTO CREATURE VALUES (39,176,24,43,26,38, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 4))",
                    "INSERT INTO CREATURE VALUES (40,152,6,10,17,18, (SELECT L.L_ID FROM LOCATION L WHERE L.L_ID = 5))",
                    // ABILITY
                    "INSERT INTO ABILITY VALUES (1, 5, 10, 0, 0, 0, 0, (SELECT C.ID FROM CREATURE C WHERE C.ID = 1))",
                    "INSERT INTO ABILITY VALUES (2, 10, 20, 1, 1, 5, 10, (SELECT C.ID FROM CREATURE C WHERE C.ID = 2))",
                    "INSERT INTO ABILITY VALUES (3, 15, 30, 0, 2, 30, 3, (SELECT C.ID FROM CREATURE C WHERE C.ID = 3))",
                    "INSERT INTO ABILITY VALUES (4, 20, 40, 1, 0, 40, 3, (SELECT C.ID FROM CREATURE C WHERE C.ID = 4))",
                    "INSERT INTO ABILITY VALUES (5, 25, 50, 0, 1, 0, 0, (SELECT C.ID FROM CREATURE C WHERE C.ID = 5))",
                    // CREATURE_HAS_POSSIBLE_AREAS
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 1), 0)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 2), 1)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 3), 2)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 4), 0)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 5), 1)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 6), 2)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 7), 0)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 8), 1)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 9), 2)",
                    "INSERT INTO CREATURE_HAS_POSSIBLE_AREAS VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 10), 0)",
                    // CREATURE_LIKES_HATES_PLAYER
                    "INSERT INTO CREATURE_LIKES_HATES_PLAYER VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 1), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster'), 0)",
                    "INSERT INTO CREATURE_LIKES_HATES_PLAYER VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 2), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster2'), 1)",
                    "INSERT INTO CREATURE_LIKES_HATES_PLAYER VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 3), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster3'), 0)",
                    "INSERT INTO CREATURE_LIKES_HATES_PLAYER VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 4), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster4'), 1)",
                    "INSERT INTO CREATURE_LIKES_HATES_PLAYER VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 5), (SELECT P.Player_Login FROM PLAYER P WHERE P.Player_Login = 'Noobmaster5'), 0)",
                    // CREATURE_LIKES_HATES_CREATURE
                    "INSERT INTO CREATURE_LIKES_HATES_CREATURE VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 1), (SELECT C.ID FROM CREATURE C WHERE C.ID = 2), 1)",
                    "INSERT INTO CREATURE_LIKES_HATES_CREATURE VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 3), (SELECT C.ID FROM CREATURE C WHERE C.ID = 4), 0)",
                    "INSERT INTO CREATURE_LIKES_HATES_CREATURE VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 5), (SELECT C.ID FROM CREATURE C WHERE C.ID = 6), 1)",
                    "INSERT INTO CREATURE_LIKES_HATES_CREATURE VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 7), (SELECT C.ID FROM CREATURE C WHERE C.ID = 8), 0)",
                    "INSERT INTO CREATURE_LIKES_HATES_CREATURE VALUES ((SELECT C.ID FROM CREATURE C WHERE C.ID = 9), (SELECT C.ID FROM CREATURE C WHERE C.ID = 10), 1)",
                    // ITEM
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1, 1000, 500, 1, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Leeroy Jenkins'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Leeroy Jenkins'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (501, 7, 10, 2, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Leeroy Jenkins'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1001, 14, 15, 3, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Leeroy Jenkins'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Leeroy Jenkins'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1501, 20, 25, 4, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Leeroy Jenkins'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (2, 999, 499, 1, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Roy'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (502, 8, 11, 2, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Roy'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Roy'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1002, 15, 16, 3, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Roy'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1502, 21, 26, 4, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Roy'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Roy'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (3, 998, 498, 5, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Moss'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Moss'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (503, 9, 12, 6, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Moss'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1003, 16, 17, 7, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Moss'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1503, 22, 27, 8, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Moss'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Moss'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (4, 997, 497, 9, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Jen'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (504, 10, 13, 10, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Jen'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Jen'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1004, 23, 28, NULL, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Jen'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Jen'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1504, 17, 18, NULL, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Jen'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (5, 996, 496, NULL, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Douglas'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Douglas'))",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (505, 11, 14, NULL, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Douglas'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1005, 18, 19, NULL, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Douglas'), NULL)",
                    "INSERT INTO ITEM (ID, Volume, Weight, L_ID, O_Name, W_Name) VALUES (1505, 24, 29, NULL, (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Douglas'), (SELECT C_Name FROM P_CHARACTER WHERE C_Name = 'Douglas'))",
                    // CONTAINER
                    "INSERT INTO CONTAINER VALUES (1, 1000, 500, (SELECT I.ID FROM ITEM I WHERE I.ID = 1))",
                    "INSERT INTO CONTAINER VALUES (2, 999, 499, (SELECT I.ID FROM ITEM I WHERE I.ID = 2))",
                    "INSERT INTO CONTAINER VALUES (3, 998, 498, (SELECT I.ID FROM ITEM I WHERE I.ID = 3))",
                    "INSERT INTO CONTAINER VALUES (4, 997, 497, (SELECT I.ID FROM ITEM I WHERE I.ID = 4))",
                    "INSERT INTO CONTAINER VALUES (5, 996, 496, (SELECT I.ID FROM ITEM I WHERE I.ID = 5))",
                    // ARMOR
                    "INSERT INTO ARMOR VALUES (1, 0, 50, (SELECT I.ID FROM ITEM I WHERE I.ID = 501))",
                    "INSERT INTO ARMOR VALUES (2, 1, 25, (SELECT I.ID FROM ITEM I WHERE I.ID = 502))",
                    "INSERT INTO ARMOR VALUES (3, 2, 74, (SELECT I.ID FROM ITEM I WHERE I.ID = 503))",
                    "INSERT INTO ARMOR VALUES (4, 3, 75, (SELECT I.ID FROM ITEM I WHERE I.ID = 504))",
                    "INSERT INTO ARMOR VALUES (5, 2, 100, (SELECT I.ID FROM ITEM I WHERE I.ID = 505))",
                    // WEAPON
                    "INSERT INTO WEAPON VALUES (1, (SELECT A.ID FROM ABILITY A WHERE A.ID = 1), (SELECT I.ID FROM ITEM I WHERE I.ID = 1001))",
                    "INSERT INTO WEAPON VALUES (2, NULL, (SELECT I.ID FROM ITEM I WHERE I.ID = 1002))",
                    "INSERT INTO WEAPON VALUES (3, NULL, (SELECT I.ID FROM ITEM I WHERE I.ID = 1003))",
                    "INSERT INTO WEAPON VALUES (4, NULL, (SELECT I.ID FROM ITEM I WHERE I.ID = 1004))",
                    "INSERT INTO WEAPON VALUES (5, NULL, (SELECT I.ID FROM ITEM I WHERE I.ID = 1005))",
                    // GENERIC_ITEM
                    "INSERT INTO GENERIC_ITEM VALUES (1, (SELECT I.ID FROM ITEM I WHERE I.ID = 1501))",
                    "INSERT INTO GENERIC_ITEM VALUES (2, (SELECT I.ID FROM ITEM I WHERE I.ID = 1502))",
                    "INSERT INTO GENERIC_ITEM VALUES (3, (SELECT I.ID FROM ITEM I WHERE I.ID = 1503))",
                    "INSERT INTO GENERIC_ITEM VALUES (4, (SELECT I.ID FROM ITEM I WHERE I.ID = 1504))",
                    "INSERT INTO GENERIC_ITEM VALUES (5, (SELECT I.ID FROM ITEM I WHERE I.ID = 1505))"
            };

    for (int i = 0; i < insert_statements.length; i++) {
      insertData = new String(insert_statements[i]);
      stmt.executeUpdate(insertData);
      System.out.println("yeet "+ insert_statements[i]);
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

