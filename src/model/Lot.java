package model;

public class Lot {
	private String mLotName;
	private String mLocation;
	private int mCapcity;
	private int mFloors;
	
	public Lot(String theLotName, String theLocation, int theCapacity, int theFloors) {
		this.mLotName = theLocation;
		this.mLocation = theLocation;
		this.mCapcity = theCapacity;
		this.mFloors = theFloors;
	}
	
	public void setLotName(final String theName) {
		if (theName == null || theName.length() == 0) {
			
		}
	}
	
	
}
