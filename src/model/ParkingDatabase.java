package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that consists of the database operations. For example insert, update.
 *
 * @author Matthew Chen,
 */
public class ParkingDatabase {
	private static String userName = "tuandinh";
	private static String password = "tuandinh";
	private static String serverName = "parkinglot.cfjkfmfnydy4.us-east-2.rds.amazonaws.com:3306/PARKING";
//	private static String userName = "chen7410"; //TODO - Change to yours
//	private static String password = "AsgufNum"; //TODO - Change to yours
//	private static String serverName = "cssgate.insttech.washington.edu/chen7410"; //TODO - Change to yours
	private static Connection sConnection;
	private List<Lot> mLots;
	private List<Space> mSpaces;

	/**
	 * Creates a sql connection to MySQL using the properties for
	 * userid, password and server information.
	 *
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		sConnection = DriverManager
				.getConnection("jdbc:mysql://" + serverName + "?user=" + userName + "&password=" + password + "&useSSL=false");

		System.out.println("Connected to database");
	}

	/**
	 * Returns a list of space objects from the database.
	 *
	 * @return list of space
	 * @throws Exception
	 */
	public List<Space> getSpaces() throws Exception {
		if (sConnection == null) {
			createConnection();
		}

		Statement statement = null;
		String query = "SELECT * FROM Space";
		mSpaces = new ArrayList<Space>();

		try {
			statement = sConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				int spaceNumber = result.getInt("spaceNumber");
				String spaceType = result.getString("spaceType");
				String lotName = result.getString("lotName");

				Space lot = new Space(spaceNumber, spaceType, lotName);
				mSpaces.add(lot);
			}
		} catch (SQLException theException) {
			theException.printStackTrace();
			throw new Exception("Unable to retrieve list of lots" + theException.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return mSpaces;
	}
	
	/**
	 * Returns a list of lot objects from the database.
	 *
	 * @return list of lot
	 * @throws Exception
	 */
	public List<Lot> getLots() throws Exception {
		if (sConnection == null) {
			createConnection();
		}

		Statement statement = null;
		String query = "SELECT * FROM Lot";
		mLots = new ArrayList<Lot>();

		try {
			statement = sConnection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String lotName = result.getString("lotName");
				String location = result.getString("location");
				int capacity = result.getInt("capacity");
				int floors = result.getInt("floors");

				Lot lot = new Lot(lotName, location, capacity, floors);
				mLots.add(lot);
			}
		} catch (SQLException theException) {
			theException.printStackTrace();
			throw new Exception("Unable to retrieve list of lots" + theException.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return mLots;
	}

	/**
	 * Filters the lot list to find the given lot name. Returns a list with the
	 * lot objects that match the lot name provided.
	 *
	 * @param theLotName
	 * @return list of lots that contain the lot name.
	 */
	public List<Lot> getLots(String theLotName) throws Exception {
		List<Lot> filterList = new ArrayList<Lot>();
		try {
			mLots = getLots();
		} catch (SQLException theException) {
			theException.printStackTrace();
			throw new Exception("Unable to retrieve lots: " + theException.getMessage());
		}
		for (Lot lot : mLots) {
			if (lot.getLotName().toLowerCase().contains(theLotName.toLowerCase())) {
				filterList.add(lot);
			}
		}
		return filterList;
	}

	/**
	 * Adds a new space to the Space table.
	 *
	 * @param theSpace
	 * @throws Exception
	 */
	public void addSpace(final Space theSpace) throws Exception {
		String sql = "INSERT Space VALUES " + "(?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = sConnection.prepareStatement(sql);
			preparedStatement.setInt(1, theSpace.getSpaceNumber());
			preparedStatement.setString(2, theSpace.getSpaceType());
			preparedStatement.setString(3, theSpace.getLotName());
			preparedStatement.executeUpdate();
		} catch (SQLException theException) {
			theException.printStackTrace();
			throw new Exception("Unable to add Space: " + theException.getMessage());
		}
	}
	
	
	/**
	 * Adds a new lot to the Lot table.
	 *
	 * @param theLot
	 * @throws Exception
	 */
	public void addLot(Lot theLot) throws Exception {
		String sql = "INSERT Lot VALUES " + "(?, ?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = sConnection.prepareStatement(sql);
			preparedStatement.setString(1, theLot.getLotName());
			preparedStatement.setString(2, theLot.getLocation());
			preparedStatement.setInt(3, theLot.getCapacity());
			preparedStatement.setInt(4, theLot.getFloors());
			preparedStatement.executeUpdate();
		} catch (SQLException theException) {
			theException.printStackTrace();
			throw new Exception("Unable to add Lot: " + theException.getMessage());
		}
	}


	/**
	 * Modifies the lot information corresponding to the index in the list.
	 *
	 * @param row        index of the element in the list
	 * @param columnName attribute to modify
	 * @param data       value to supply
	 * @throws Exception
	 */
	public void updateLot(int row, String columnName, Object data) throws Exception {
		Lot lot = mLots.get(row);
		String lotName = lot.getLotName();
		String sql = "update Lot set " + columnName + " = ?  where lotName = ? ";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = sConnection.prepareStatement(sql);
			if (data instanceof String) {
				preparedStatement.setString(1, (String) data);
			}
			preparedStatement.setString(2, lotName);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Unable to add Lot: " + e.getMessage());
		} 
	}
	
	/**
	 * Modifies the space information corresponding to the index in the list.
	 *
	 * @param row        index of the element in the list
	 * @param columnName attribute to modify
	 * @param data       value to supply
	 * @throws Exception
	 */
	public void updateSpace(int row, String columnName, Object data) throws Exception {
		Space space = mSpaces.get(row);
		int spaceNumber = space.getSpaceNumber();
		
		String sql = "update Space set " + columnName + " = ?  where spaceNumber = ? ";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = sConnection.prepareStatement(sql);
			if (data instanceof String) {
				preparedStatement.setString(1, (String) data);
			}
			preparedStatement.setInt(2, spaceNumber);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Unable to add Space: " + e.getMessage());
		} 
	}

	

}
