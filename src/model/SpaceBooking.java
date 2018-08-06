package model;

public class SpaceBooking {
    private int myBookingID;
    private int mySpaceNumber;
    private int myStaffNumber;
    private String myVisitorLicense;
    private String myVisitDate;

    public SpaceBooking(final int theBookingID, final int theSpaceNumber, final int theStaffNumber, final String theVisitorLicense, final String theVisitDate) {
        if (theBookingID < 0) {
            throw new IllegalArgumentException("Booking ID cannot be negative.");
        }
        if (theSpaceNumber < 0) {
            throw new IllegalArgumentException("Space number cannot be negative.");
        }
        if (theStaffNumber < 0) {
            throw new IllegalArgumentException("Staff number cannot be negative.");
        }
        if (theVisitorLicense.isEmpty()) {
            throw new IllegalArgumentException("License number cannot be empty");
        }
        myBookingID = theBookingID;
        mySpaceNumber = theSpaceNumber;
        myStaffNumber = theStaffNumber;
        myVisitorLicense = theVisitorLicense;
        myVisitDate = theVisitDate;
    }
}
