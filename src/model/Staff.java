package model;

public class Staff {
    private int myStaffNumber;
    private int myPhoneExt;
    private String myLicenseNumber;

    public Staff(final int theStaffNumber, final int thePhoneExt, final String theLicenseNumber) {
        if (theStaffNumber < 0) {
            throw new IllegalArgumentException("Staff number cannot be negative.");
        }
        if (thePhoneExt < 0) {
            throw new IllegalArgumentException("Phone extension cannot be negative.");
        }
        if (theLicenseNumber.isEmpty()) {
            throw new IllegalArgumentException("License number cannot be empty");
        }
        myStaffNumber = theStaffNumber;
        myPhoneExt = thePhoneExt;
        myLicenseNumber = theLicenseNumber;
    }
}
