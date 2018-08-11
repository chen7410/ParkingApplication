package model;

/**
 * Represents a Staff with StaffNumber, PhoneExt and LicenseNumber.
 * @author Matthew Chen, Tuan Dinh
 *
 */
public class Staff {
    private int myStaffNumber;
    private String myPhoneExt;
    private String myLicenseNumber;

    /**
     * Initialize a Staff.
     * @param theStaffNumber
     * @param thePhoneExt
     * @param theLicenseNumber
     */
    public Staff(final int theStaffNumber, final String thePhoneExt, final String theLicenseNumber) {
        if (theStaffNumber < 0) {
            throw new IllegalArgumentException("Staff number cannot be negative.");
        }
        if (!thePhoneExt.matches("\\d+")) {
            throw new IllegalArgumentException("Phone extension must be a number");
        }
        if (theLicenseNumber == null || theLicenseNumber.isEmpty()) {
            throw new IllegalArgumentException("License number cannot be empty");
        }
        myStaffNumber = theStaffNumber;
        myPhoneExt = thePhoneExt;
        myLicenseNumber = theLicenseNumber;
    }

    /**
     * return the staff number of this Staff.
     * @return
     */
    public int getStaffNumber() {
        return myStaffNumber;
    }

    /**
     * return the phone extension of this Staff.
     * @return
     */
    public String getPhoneExt() {
        return myPhoneExt;
    }

    /**
     * return the license number of this Staff.
     * @return
     */
    public String getLicenseNumber() {
        return myLicenseNumber;
    }
}
