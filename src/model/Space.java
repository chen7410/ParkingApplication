package model;
/**
 * Represents a Space with lotName, spaceBumber and space Type.
 * @author Matthew Chen, Tuan Dinh
 *
 */
public class Space {

    private int mySpaceNumber;
    private String myLotName;
    private String mySpaceType;

    /**
     * Initialize a sSpace.
     * @param theSpaceNumber
     * @param theSpaceType
     * @param theLotName
     */
    public Space(final int theSpaceNumber, final String theSpaceType, 
    		final String theLotName) {
        if (theSpaceNumber <= 0) {
            throw new IllegalArgumentException("Space number cannot be less than 1.");
        }

        if (theLotName == null || theLotName.isEmpty()){
            throw new IllegalArgumentException("Lot name cannot be empty.");
        }
        
        if (theSpaceType == null || theSpaceType.isEmpty()) {
        	throw new IllegalArgumentException("Space type cannot be empty.");
        }
        mySpaceNumber = theSpaceNumber;
        myLotName = theLotName;
        mySpaceType = theSpaceType;
    }
 

    /**
     * return the space number of this Space.
     * @return
     */
    public int getSpaceNumber() {
        return mySpaceNumber;
    }

    /**
     * return the lot name of this Space
     * @return
     */
    public String getLotName() {
        return myLotName;
    }
    
    /**
     * return the space type of this Space.
     * @return
     */
    public String getSpaceType() {
    	return mySpaceType;
    }
}
