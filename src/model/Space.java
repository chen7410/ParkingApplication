package model;

public class Space {

    private int mySpaceNumber;
    private String myLotName;
    private String mySpaceType;

    public Space(final int theSpaceNumber, final String theSpaceType, 
    		final String theLotName) {
        if (theSpaceNumber <= 0) {
            throw new IllegalArgumentException("Space number cannot be less than 1.");
        }

        if (theLotName.isEmpty()){
            throw new IllegalArgumentException("Lot name cannot be empty.");
        }
        
        if (theSpaceType == null || theSpaceType.isEmpty()) {
        	throw new IllegalArgumentException("Space type cannot be empty.");
        }
        mySpaceNumber = theSpaceNumber;
        myLotName = theLotName;
        mySpaceType = theSpaceType;
    }
    
    public Space(final int theSpaceNumber, final String theLotName) {
        if (theSpaceNumber <= 0) {
            throw new IllegalArgumentException("Space number cannot be less than 1.");
        }

        if (theLotName.isEmpty()){
            throw new IllegalArgumentException("Lot name cannot be empty.");
        }
        mySpaceNumber = theSpaceNumber;
        myLotName = theLotName;
    }

    public int getSpaceNumber() {
        return mySpaceNumber;
    }

    public String getLotName() {
        return myLotName;
    }
    
    public String getSpaceType() {
    	return mySpaceType;
    }
}
