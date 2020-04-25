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

	public static Vector<String> getCreaturesInRoom(int roomID) {
		Vector<String> result = new Vector<String>();
		
        try {
        	
            String selectData = "CALL get_creatures(?)";

            PreparedStatement stmt = connection.prepareStatement(selectData);
			stmt.setInt(1, roomID);
			stmt.execute();
			
			ResultSet rs = stmt.getResultSet();
			
			while (rs.next()) {
				
				String data = rs.getNString("Creature");
				result.add(data);
			}

        } catch (SQLException e) {
        	e.printStackTrace();
        }
 
        return result;
        
//        try {
//        	
//            String selectData = new String("SELECT * FROM CREATURE WHERE L_ID (Col1, Col2, Col3, Col4, Col5) VALUES (?,?,?,?,?)");
//
//            PreparedStatement stmt = connection.prepareStatement(selectData);
//			stmt.setInt(1, roomID);
//
//            ResultSet rs = stmt.executeQuery(selectData);
//            
//            for (int i=0; rs.next(); i++) {
//            	
//               result.add( rs.getString(i) );
//               
//            }
//        } catch (SQLException e) {
//        	e.printStackTrace();
//        }
// 
//        return result;
        
		
	}

	public static void setStoredProcedures() {
		
	    Statement stmt;
		try {
			stmt = connection.createStatement();
		    String changeDelimiter = new String("DELIMITER //");
		    stmt.executeUpdate(changeDelimiter);
		    String storedProcedure1 = new String("CREATE PROCEDURE get_creatures (IN n int)"
		    		+ " BEGIN SELECT * FROM CREATURE WHERE L_ID = n; END//");
		    stmt.executeUpdate(storedProcedure1);
//		    String storedProcedure2 = new String("CREATE PROCEDURE get_items (IN n int)"
//		    		+ " BEGIN SELECT * FROM ITEM WHERE L_ID = n; END//");
//		    stmt.executeUpdate(storedProcedure2);
		    String changeDelimiterBack = new String("DELIMITER ;");
		    stmt.executeUpdate(changeDelimiterBack);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

	
}
