package model;

/**
 * Represent a covered space with space number and monthly rate.
 * @author Matthew Chen, TuanDinh.
 *
 */
public class CoveredSpace {

    private float myMonthlyRate;
    private int mySpaceNumber;
    
    public CoveredSpace(final int theSpaceNumber, final float theMonthlyRate) {
        if (theMonthlyRate < 0) {
            throw new IllegalArgumentException("Monthly rate cannot be negative.");
        }
        if (theSpaceNumber < 0) {
            throw new IllegalArgumentException("Space number cannot be negative.");
        }
        myMonthlyRate = theMonthlyRate;
        mySpaceNumber = theSpaceNumber;
    }
    
   /**
    * return the space number of this CoveredSpace.
    * @return
    */
    public int getSpaceNumber() {
    	return mySpaceNumber;
    }
    
    /**
     * return the monthly rate of this CoveredSpace.
     * @return
     */
    public float getMonthlyRate() {
        return myMonthlyRate;
    }
}
