package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ParkingDatabase {
		private static String userName = "tuandinh"; //TODO - Change to yours
		private static String password = "tuandinh"; //TODO - Change to yours
		private static String serverName = "parkinglot.cfjkfmfnydy4.us-east-2.rds.amazonaws.com:3306/PARKING"; //TODO - Change to yours
		private static Connection sConnection;
		
		/**
		 * Creates a sql connection to MySQL using the properties for
		 * userid, password and server information.
		 * @throws SQLException
		 */
		public static void createConnection() throws SQLException {
			sConnection =  DriverManager
					.getConnection("jdbc:mysql://" + serverName + "?user=" + userName + "&password=" + password);
		
			System.out.println("Connected to database");
		}
}
