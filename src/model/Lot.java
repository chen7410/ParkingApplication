package model;

/**
 * Represents a lot with lotName, location, capacity and floors.
 * @author Matthew Chen,
 *
 */
public class Lot {
	private String mLotName;
	private String mLocation;
	private int mCapcity;
	private int mFloors;
	
	/**
	 * Initialize the lot.
	 * @param theLotName
	 * @param theLocation
	 * @param theCapacity
	 * @param theFloors
	 * @throws IllegalArgumentException if one or more the parameters are null.
	 */
	public Lot(final String theLotName, final String theLocation, 
			final int theCapacity, final int theFloors) {
		setLotName(theLotName);
		setLocation(theLocation);
		setCapacity(theCapacity);
		setFloors(theFloors);
	}
	
	/**
	 * Set the lot namne.
	 * @param theName
	 * @throws IllegalArgumentException if theName is null or empty.
	 */
	public void setLotName(final String theName) {
		if (theName == null || theName.length() == 0) {
			throw new IllegalArgumentException("Please supply a valid lot name.");
		}
		this.mLotName = theName;
	}
	
	/**
	 * Set the lot location.
	 * @param theLocation
	 * @throws IllegalArgumentException if theLocation is null or empty.
	 */
	public void setLocation(final String theLocation) {
		if (theLocation == null || theLocation.length() == 0) {
			throw new IllegalArgumentException("Please supply a valid location.");
		}
		this.mLocation = theLocation;
	}
	
	/**
	 * Set the lot capacity.
	 * @param theCapacity
	 * @throws IllegalArgumentException if theCapacity is < 1.
	 */
	public void setCapacity(final int theCapacity) {
		if (theCapacity < 1) {
			throw new IllegalArgumentException("Please supply a valid capacity.");
		}
		this.mCapcity = theCapacity;
	}
	
	/**
	 * Set the lot floors.
	 * @param theFloors
	 * @throws IllegalArgumentException if theFloors is  < 1.
	 */
	public void setFloors(final int theFloors) {
		if (theFloors < 1) {
			throw new IllegalArgumentException("Please supply a valid floors.");
		}
		this.mFloors = theFloors;
	}
	
	/**
	 * Return the lot name of this lot.
	 * @return the lot name.
	 */
	public String getLotName() {
		return mLotName;
	}
	
	/**
	 * Return the lot location of this lot.
	 * @return the lot location.
	 */
	public String getLocation() {
		return mLocation;
	}
	
	/**
	 * Return the lot capacity of this lot.
	 * @return the lot capacity.
	 */
	public int getCapacity() {
		return mCapcity;
	}
	
	/**
	 * Return the lot floors of this lot.
	 * @return the lot floors.
	 */
	public int getFloors() {
		return mFloors;
	}
	
}
