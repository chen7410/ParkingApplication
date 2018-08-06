package model;

public class CoveredSpace extends Space {

    private float myMonthlyRate;

    public CoveredSpace(final int theSpaceNumber, final String theLotName, final float theMonthlyRate) {
        super(theSpaceNumber, "Covered", theLotName);
        if (theMonthlyRate < 0) {
            throw new IllegalArgumentException("Monthly rate cannot be less than 0.");
        }
        myMonthlyRate = theMonthlyRate;
    }

    public float getMonthlyRate() {
        return myMonthlyRate;
    }
}
