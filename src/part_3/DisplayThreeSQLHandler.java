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
	
}
