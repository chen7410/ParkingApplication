package model;

public class Staff {
    private int myStaffNumber;
    private String myPhoneExt;
    private String myLicenseNumber;

    public Staff(final int theStaffNumber, final String thePhoneExt, final String theLicenseNumber) {
        if (theStaffNumber < 0) {
            throw new IllegalArgumentException("Staff number cannot be negative.");
        }
        if (!thePhoneExt.matches("\\d+")) {
            throw new IllegalArgumentException("Phone extension must be a number");
        }
        if (theLicenseNumber.isEmpty()) {
            throw new IllegalArgumentException("License number cannot be empty");
        }
        myStaffNumber = theStaffNumber;
        myPhoneExt = thePhoneExt;
        myLicenseNumber = theLicenseNumber;
    }

    public int getStaffNumber() {
        return myStaffNumber;
    }

    public String getPhoneExt() {
        return myPhoneExt;
    }

    public String getLicenseNumber() {
        return myLicenseNumber;
    }
}
