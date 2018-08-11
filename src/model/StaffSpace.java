package model;

/**
 * Represents a Staff Space with StaffNumber and SpaceNumber.
 * @author Matthew Chen, Tuan Dinh
 *
 */
public class StaffSpace {
    private int myStaffNumber;
    private int mySpaceNumber;

    /**
     * Initializes a StaffSpace.
     * @param theStaffNumber
     * @param theSpaceNumber
     * @throws IllegalArgumentException if theStaffNumber or theSpaceNumber < 0.
     */
    public StaffSpace(final int theStaffNumber, final int theSpaceNumber) {
        if (theStaffNumber < 0) {
            throw new IllegalArgumentException("Staff number cannot be negative.");
        }
        if (theSpaceNumber < 0) {
            throw new IllegalArgumentException("Space number cannot be negative.");
        }
        myStaffNumber = theStaffNumber;
        mySpaceNumber = theSpaceNumber;
    }
    
    /**
     * return the staff number.
     * @return
     */
    public int getStaffNumber() {
    	return myStaffNumber;
    }
    
    /**
     * return the spae number.
     * @return
     */
    public int getSpaceNumber() {
    	return mySpaceNumber;
    }
}
