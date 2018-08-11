package model;

/**
 * Represents a SpaceBooking with BookingID, SpaceNumber, space Type, StaffNumber, VisitorLicense, and the VisitDate.
 * @author Matthew Chen, Tuan Dinh
 *
 */
public class SpaceBooking {
    private int myBookingID;
    private int mySpaceNumber;
    private int myStaffNumber;
    private String myVisitorLicense;
    private String myVisitDate;

    /**
     *  Initialize a SpaceBooking.
     * @param theBookingID
     * @param theSpaceNumber
     * @param theStaffNumber
     * @param theVisitorLicense
     * @param theVisitDate
     */
    public SpaceBooking(final int theBookingID, final int theSpaceNumber, 
    		final int theStaffNumber, final String theVisitorLicense, 
    		final String theVisitDate) {
        if (theBookingID < 0) {
            throw new IllegalArgumentException("Booking ID cannot be negative.");
        }
        if (theSpaceNumber < 0) {
            throw new IllegalArgumentException("Space number cannot be negative.");
        }
        if (theStaffNumber < 0) {
            throw new IllegalArgumentException("Staff number cannot be negative.");
        }
        if (theVisitorLicense != null && theVisitorLicense.isEmpty()) {
            throw new IllegalArgumentException("License number cannot be empty");
        }
        myBookingID = theBookingID;
        mySpaceNumber = theSpaceNumber;
        myStaffNumber = theStaffNumber;
        myVisitorLicense = theVisitorLicense;
        myVisitDate = theVisitDate;
    }

    /**
     * return the booking ID of this SpaceBooking.
     * @return
     */
    public int getBookingID() {
        return myBookingID;
    }

    /**
     * return the space number of this SpaceBooking.
     * @return
     */
    public int getSpaceNumber() {
        return mySpaceNumber;
    }

    /**
     * return the staff number of this SpaceBooking.
     * @return
     */
    public int getStaffNumber() {
        return myStaffNumber;
    }

    /**
     * return the visitor license of this SpaceBooking.
     * @return
     */
    public String getVisitorLicense() {
        return myVisitorLicense;
    }

    /**
     * return the visit date of this SpaceBooking.
     * @return
     */
    public String getVisitorDate() {
        return myVisitDate;
    }
}
