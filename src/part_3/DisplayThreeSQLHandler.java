package part_3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DisplayThreeSQLHandler {
	
	private static Connection connection;
	
//	public DisplayThreeSQLHandler() {}
	
	public static void setConnection(Connection c) {
		connection = c;
	}
	

	public static Vector<String> getRooms() {
		Vector<String> result = new Vector<String>();
			
        try {
        	
            String selectData = "SELECT * FROM LOCATION";

            PreparedStatement stmt = connection.prepareStatement(selectData);
			stmt.execute();
			
			ResultSet rs = stmt.getResultSet();
			
			while (rs.next()) {
				
				String data = rs.getString("L_ID");//getNString(1);
				result.add(data);
			}

        } catch (SQLException e) {
        	e.printStackTrace();
        }
 
        return result;    
		

	}

	public static Vector<String> getCreaturesInRoom(int roomID) {
		Vector<String> result = new Vector<String>();

		
        try {
            String selectData = "CALL get_creatures(?)";

            PreparedStatement stmt = connection.prepareCall(selectData);
			stmt.setInt(1, roomID);
			stmt.execute();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				String data = rs.getString("ID");
				result.add(data);

			}

        } catch (SQLException e) {
        	e.printStackTrace();
        }
 

        return result;        
		
	}
	

	public static Vector<String> getItemsInRoom(int roomID) {
		
		Vector<String> result = new Vector<String>();
		int i = 0;
		
        try {
	        String selectData = "SELECT * FROM GENERIC_ITEM WHERE L_ID = " + roomID;
	        PreparedStatement stmt = connection.prepareStatement(selectData);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				String data = rs.getString("GI_ID");
				result.add(data);
				i++;
			}
			DisplayThree.getInstance().setWhereNewItemTypesBeginInItemSelector(1, i);
			
	        selectData = "SELECT * ARMORS WHERE L_ID = " + roomID;
	        stmt = connection.prepareStatement(selectData);
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				String data = rs.getString("A_ID");
				result.add(data);
			}
			DisplayThree.getInstance().setWhereNewItemTypesBeginInItemSelector(2, i);


        } catch (SQLException e) {
        	e.printStackTrace();
        }
 

        return result; 
		
	}

	public static void setStoredProcedures() {
		
		try {
			
            String selectData = "DROP PROCEDURE IF EXISTS get_creatures";
            PreparedStatement stmt = connection.prepareStatement(selectData);
			stmt.execute();
			
		
            selectData = "CREATE PROCEDURE get_creatures(IN n INT) BEGIN SELECT * FROM CREATURE WHERE L_ID = n; END";
            stmt = connection.prepareStatement(selectData);
			stmt.execute();
			
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteCreature(String ID, int L_ID) {

		try {
			
//	        String selectData = "ALTER TABLE ABILITY DROP FOREIGN KEY ABILITY_ibfk_1";
//	        PreparedStatement stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE ABILITY DROP FOREIGN KEY ABILITY_ibfk_2";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE CREATURE_LIKES_HATES_CREATURE DROP FOREIGN KEY CREATURE_LIKES_HATES_CREATURE_ibfk_1";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE CREATURE_LIKES_HATES_CREATURE DROP FOREIGN KEY CREATURE_LIKES_HATES_CREATURE_ibfk_2";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE CREATURE_HAS_POSSIBLE_AREAS DROP FOREIGN KEY CREATURE_HAS_POSSIBLE_AREAS_ibfk_1";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE CREATURE_LIKES_HATES_PLAYER DROP FOREIGN KEY CREATURE_LIKES_HATES_PLAYER_ibfk_1";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
			
			
	        String selectData = "DELETE FROM CREATURE WHERE ID = " + ID + " AND L_ID = " + L_ID;
	        PreparedStatement stmt = connection.prepareStatement(selectData);
			stmt.execute();
			
			
//	        selectData = "ALTER TABLE ABILITY ADD FOREIGN KEY (CREATURE_ID) REFERENCES CREATURE(ID)";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE ABILITY ADD FOREIGN KEY (CREATURE_ID) REFERENCES CREATURE(ID)";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE CREATURE_LIKES_HATES_CREATURE ADD FOREIGN KEY (CREATURE_ID) REFERENCES CREATURE(ID)";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE CREATURE_LIKES_HATES_CREATURE ADD FOREIGN KEY (CREATURE_ID) REFERENCES CREATURE(ID)";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE CREATURE_HAS_POSSIBLE_AREAS ADD FOREIGN KEY (CREATURE_ID) REFERENCES CREATURE(ID)";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();
//			
//	        selectData = "ALTER TABLE CREATURE_LIKES_HATES_PLAYER ADD FOREIGN KEY (CREATURE_ID) REFERENCES CREATURE(ID)";
//			stmt = connection.prepareStatement(selectData);
//			stmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
