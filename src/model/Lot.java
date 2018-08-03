package model;

public class Lot {
	private String mLotName;
	private String mLocation;
	private int mCapcity;
	private int mFloors;
	
	public Lot(final String theLotName, final String theLocation, 
			final int theCapacity, final int theFloors) {
		
	}
	
	public void setLotName(final String theName) {
		if (theName == null || theName.length() == 0) {
			throw new IllegalArgumentException("Please supply a valid lot name.");
		}
		this.mLotName = theName;
	}
	
	public void setLocation(final String theLocation) {
		if (theLocation == null || theLocation.length() == 0) {
			throw new IllegalArgumentException("Please supply a valid location.");
		}
		this.mLocation = theLocation;
	}
	
}
