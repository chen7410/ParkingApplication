package model;

public class StaffSpace {
    private int myStaffNumber;
    private int mySpaceNumber;

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
}
