package model;

public abstract class Space {

    private int mySpaceNumber;
    private String myLotName;

    protected Space(final int theSpaceNumber, final String theLotName) {
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
}
