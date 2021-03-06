package model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that consists of the database operations. For example insert, update.
 *
 * @author Matthew Chen, Tuan Dinh
 */
public class ParkingDatabase {
    private static final String USERNAME = "chen7410";
    private static final String PASSWORD = "AsgufNum";
    private static final String SERVER_NAME = "cssgate.insttech.washington.edu/chen7410";
    private static Connection mConnection;
    private List<Lot> mLots;
    private List<Space> mSpaces;
    private List<Staff> mStaff;
    private List<SpaceBooking> mSpaceBooking;
    private List<CoveredSpace> mCoveredSpace;

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
    public List<CoveredSpace> getStaffSpace() throws Exception {
        if (mConnection == null) {
            createConnection();
        }

        Statement statement = null;
        String query = "SELECT * FROM CoveredSpace WHERE spaceNumber NOT IN "
                + "(SELECT spaceNumber FROM StaffSpace) AND "
                + "spaceNumber NOT IN (SELECT spaceNumber FROM SpaceBooking)";
        mCoveredSpace = new ArrayList<>();

        try {
            statement = mConnection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                float monthlyRate = result.getFloat("monthlyRate");
                int spaceNumber = result.getInt("spaceNumber");

                mCoveredSpace.add(new CoveredSpace(spaceNumber, monthlyRate));
            }
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to retrieve list of staffsSpaces" + theException.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return mCoveredSpace;
    }

    /**
     * Returns a list of staff space information from the database.
     *
     * @return list of all staff space information
     * @throws Exception Exception when querying from the database.
     */
    public List<SpaceBooking> getSpaceBooking() throws Exception {
        if (mConnection == null) {
            createConnection();
        }

        Statement statement = null;
        String query = "SELECT spaceNumber,DATE(dateOfVisit) AS dateOfVisit,  bookingID, staffNumber, visitorLicense " +
                "FROM CoveredSpace " +
                "NATURAL LEFT JOIN SpaceBooking " +
                "WHERE (dateOfVisit >= CURDATE() " +
                "OR dateOfVisit IS NULL) " +
                "AND spaceNumber NOT IN (SELECT spaceNumber FROM StaffSpace) " +
                "ORDER BY spaceNumber, CASE WHEN dateOfVisit IS NULL THEN 1 ELSE 0 END, dateOfVisit;";
        mSpaceBooking = new ArrayList<>();
        
        try {
            statement = mConnection.createStatement();
            ResultSet result = statement.executeQuery(query);
            int amount = 0;
            int lastID = -1;
            while (result.next()) {
                int bookingID = result.getInt("bookingID");
                int spaceNumber = result.getInt("spaceNumber");
                int staffNumber = result.getInt("staffNumber");
                String visitorLicense = result.getString("visitorLicense");
                String dateOfVisit = result.getString("dateOfVisit");
                if (lastID >= 0) {
                	if (lastID != bookingID) {
                		amount++;
                	}
                } else {
                	amount++;
                	lastID = bookingID;
                }
                if (amount > 20) {
                	break;
                }
                mSpaceBooking.add(new SpaceBooking(bookingID, spaceNumber, staffNumber, visitorLicense, dateOfVisit));
            }
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to retrieve list of booking " + theException.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return mSpaceBooking;
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


    /**
     * Adds a new space booking to the database.
     *
     * @param theSpaceBooking the space booking to be added to the database.
     * @throws Exception Exception querying the database
     */
    public void addSpaceBooking(final SpaceBooking theSpaceBooking) throws Exception {
        String query = "INSERT SpaceBooking VALUES (?, ?, ?, ?, ?); ";

        try {
            PreparedStatement preparedStatement = mConnection.prepareStatement(query);
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setInt(2, theSpaceBooking.getSpaceNumber());
            preparedStatement.setInt(3, theSpaceBooking.getStaffNumber());
            preparedStatement.setString(4, theSpaceBooking.getVisitorLicense());
            preparedStatement.setString(5, theSpaceBooking.getVisitorDate());
            preparedStatement.executeUpdate();
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to add new space booking: " + theException.getMessage());
        }
    }


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

    public List<CoveredSpace> getCoveredSpaces() throws Exception{
        if (mConnection == null) {
            createConnection();
        }

        Statement statement = null;
        String query = "SELECT * FROM CoveredSpace";
        mCoveredSpace = new ArrayList<>();

        try {
            statement = mConnection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int spaceNumber = result.getInt("spaceNumber");
                float monthlyRate = result.getFloat("monthlyRate");

                mCoveredSpace.add(new CoveredSpace(spaceNumber, monthlyRate));
            }
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to retrieve list of Covered Space" + theException.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return mCoveredSpace;
    }

    /**
     * Adds a new covered space to the database.
     *
     * @param theCoveredSpace the staff space to be added to the database.
     * @throws Exception Exception querying the database
     */
    public void addCoveredSpace(CoveredSpace theCoveredSpace) throws Exception {
        String query = "INSERT CoveredSpace VALUES (?, ?); ";

        try {
            PreparedStatement preparedStatement = mConnection.prepareStatement(query);
            preparedStatement.setInt(1, theCoveredSpace.getSpaceNumber());
            preparedStatement.setFloat(2, theCoveredSpace.getMonthlyRate());
            preparedStatement.executeUpdate();
        } catch (SQLException theException) {
            theException.printStackTrace();
            throw new Exception("Unable to add new CoveredSpace: " + theException.getMessage());
        }
    }
}
