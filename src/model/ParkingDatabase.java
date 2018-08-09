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
 * @author Matthew Chen, Tuan Dinh
 */
public class ParkingDatabase {
    private static final String USERNAME = "tuandinh";
    private static final String PASSWORD = "tuandinh";
    private static final String SERVER_NAME = "parkinglot.cfjkfmfnydy4.us-east-2.rds.amazonaws.com:3306/PARKING";
//  private static String USERNAME = "chen7410";
//	private static String PASSWORD = "AsgufNum";
//	private static String SERVER_NAME = "cssgate.insttech.washington.edu/chen7410";
    private static Connection mConnection;
    private List<Lot> mLots;
    private List<Space> mSpaces;
    private List<Staff> mStaff;
	private List<StaffSpace> mStaffSpace;

    /**
     * Creates a sql connection to MySQL using the properties for
     * user id, password and server information.
     *
     * @throws SQLException SQL exception when trying to connect to the database.
     */
    public static void createConnection() throws SQLException {
        mConnection = DriverManager
                .getConnection("jdbc:mysql://" + SERVER_NAME + "?user=" + USERNAME + "&password=" + PASSWORD + "&useSSL=false");
        System.out.println("Connected to database");
    }

    /**
     * Returns a list of space objects from the database.
     *
     * @return list of space
     * @throws Exception Exception when querying from the database.
     */
    public List<Space> getSpaces() throws Exception {
        if (mConnection == null) {
            createConnection();
        }

        Statement statement = null;
        String query = "SELECT * FROM Space";
        mSpaces = new ArrayList<Space>();

        try {
            statement = mConnection.createStatement();
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
     * Returns a list of lot information from the database.
     *
     * @return list of lot information
     * @throws Exception Exception when querying from the database.
     */
    public List<Lot> getLots() throws Exception {
        if (mConnection == null) {
            createConnection();
        }

        Statement statement = null;
        String query = "SELECT * FROM Lot";
        mLots = new ArrayList<>();

        try {
            statement = mConnection.createStatement();
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
     * Returns a list of staff space information from the database.
     *
     * @return list of all staff space information
     * @throws Exception Exception when querying from the database.
     */
    public List<StaffSpace> getStaffSpace() throws Exception {
        if (mConnection == null) {
            createConnection();
        }

        Statement statement = null;
        String query = "SELECT * FROM StaffSpace";
        mStaffSpace = new ArrayList<>();

        try {
            statement = mConnection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int staffNumber = result.getInt("staffNumber");
                int spaceNumber = result.getInt("spaceNumber");

                mStaffSpace.add(new StaffSpace(staffNumber, spaceNumber));
            }
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to retrieve list of staffsSpaces" + theException.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return mStaffSpace;
    }

    /**
     * Returns a list of staff information from the database.
     *
     * @return list of all staff information
     * @throws Exception Exception when querying from the database.
     */
    public List<Staff> getStaff() throws Exception {
        if (mConnection == null) {
            createConnection();
        }

        Statement statement = null;
        String query = "SELECT * FROM Staff";
        mStaff = new ArrayList<>();

        try {
            statement = mConnection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int staffNumber = result.getInt("staffNumber");
                String telephoneExt = result.getString("telephoneExt");
                String vehicleLicenseNumber = result.getString("vehicleLicenseNumber");

                mStaff.add(new Staff(staffNumber, telephoneExt, vehicleLicenseNumber));
            }
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to retrieve list of lots" + theException.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return mStaff;
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
        String sql = "INSERT Space VALUES (?, ?, ?); ";
        try {
            PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
            preparedStatement.setInt(1, theSpace.getSpaceNumber());
            preparedStatement.setString(2, theSpace.getSpaceType());
            preparedStatement.setString(3, theSpace.getLotName());
            preparedStatement.executeUpdate();
        } catch (SQLException theException) {
            throw new Exception("Unable to add Space: " + theException.getMessage());
        }
    }


    /**
     * Adds a new lot to the Lot table.
     *
     * @param theLot
     * @throws Exception Exception querying the database
     */
    public void addLot(final Lot theLot) throws Exception {
        String sql = "INSERT Lot VALUES (?, ?, ?, ?); ";
        try {
            PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
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
     * Adds a new staff to the database.
     *
     * @param theStaff the staff to be added to the database.
     * @throws Exception Exception querying the database
     */
    public void addStaff(final Staff theStaff) throws Exception {
        String query = "INSERT Staff VALUES (?, ?, ?); ";

        try {
            PreparedStatement preparedStatement = mConnection.prepareStatement(query);
            preparedStatement.setInt(1, theStaff.getStaffNumber());
            preparedStatement.setString(2, theStaff.getPhoneExt());
            preparedStatement.setString(3, theStaff.getLicenseNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to add new staff: " + theException.getMessage());
        }
    }


//    /**
//     * Modifies the lot information corresponding to the index in the list.
//     *
//     * @param row        index of the element in the list
//     * @param columnName attribute to modify
//     * @param data       value to supply
//     * @throws Exception Exception querying the database
//     */
//    public void updateLot(int row, String columnName, Object data) throws Exception {
//        Lot lot = mLots.get(row);
//        String lotName = lot.getLotName();
//        String sql = "update Lot set " + columnName + " = ?  where lotName = ? ";
//        try {
//            PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
//            if (columnName.equals("location")) {
//                preparedStatement.setString(1, (String) data);
//            } else {
//                preparedStatement.setInt(1, (int) data);
//            }
//            preparedStatement.setString(2, lotName);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new Exception("Unable to modify Lot: " + e.getMessage());
//        }
//    }


//    /**
//     * Modifies the space information corresponding to the index in the list.
//     *
//     * @param row        index of the element in the list
//     * @param columnName attribute to modify
//     * @param data       value to supply
//     * @throws Exception Exception querying the database
//     */
//    public void updateSpace(int row, String columnName, Object data) throws Exception {
//        Space space = mSpaces.get(row);
//        int spaceNumber = space.getSpaceNumber();
//
//        String sql = "update Space set " + columnName + " = ?  where spaceNumber = ? ";
//        try {
//            PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
//            if (data instanceof String) {
//                preparedStatement.setString(1, (String) data);
//            }
//            preparedStatement.setInt(2, spaceNumber);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new Exception("Unable to add Space: " + e.getMessage());
//        }
//    }


    /**
     * Modifies the staff information corresponding to the index in the list.
     *
     * @param theRow     index of the element in the list
     * @param columnName attribute to modify
     * @param data       value to supply
     * @throws Exception Exception querying the database
     */
    public void updateStaff(int theRow, String columnName, Object data) throws Exception {
        Staff staff = mStaff.get(theRow);
        String query = "update Staff set " + columnName + " = ?  where staffNumber = ? ";
        try {
            PreparedStatement preparedStatement = mConnection.prepareStatement(query);
            if (columnName.equals("telephoneExt")) {
                String ext = (String) data;
                if (!ext.matches("\\d+")) {
                    throw new IllegalArgumentException("Phone extension must be a number");
                }
                preparedStatement.setString(1, (String) data);
            } else {
                preparedStatement.setString(1, (String) data);
            }
            preparedStatement.setInt(2, staff.getStaffNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Unable to add Space: " + e.getMessage());
        }
    }

    /**
     * Adds a new staff space to the database.
     *
     * @param theStaffSpace the staff space to be added to the database.
     * @throws Exception Exception querying the database
     */
	public void addStaffSpace(StaffSpace theStaffSpace) throws Exception {
		String query = "INSERT StaffSpace VALUES (?, ?); ";

        try {
            PreparedStatement preparedStatement = mConnection.prepareStatement(query);
            preparedStatement.setInt(1, theStaffSpace.getStaffNumber());
            preparedStatement.setInt(2, theStaffSpace.getSpaceNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to add new staff space: " + theException.getMessage());
        }
		
	}
}
