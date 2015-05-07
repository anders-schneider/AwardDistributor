package awardDistributor;

/**
 * The Award class represents an award that can have multiple nominees
 * but only one recipient.
 * 
 * @author Anders Schneider
 *
 */

public class Award {
	
	private String name;
	private int index;
	private String[] noms;
	

	private static int numAwards = 0;
	
	/**
	 * Constructor of the Award class, takes a String representing the name
	 * of the Award
	 * @param name
	 */
	public Award(String name) {
		this.name = name;
		index = numAwards;
		numAwards++;
	}
	
	/**
	 * Returns the index of this Award: its ordinal position relative to
	 * all the other awards that have been created.
	 * 
	 * @return This Award's index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Returns the array of nominees for this Award.
	 * 
	 * @return The array of nominees for this Award
	 */
	public String[] getNoms() {
		return noms;
	}
	
	/**
	 * Returns the hashcode corresponding to this Award.
	 * 
	 * @return The hashcode corresponding to this Award.
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Compares this Award with the input object for equality. Two Awards
	 * are equal if they share the same name.
	 * 
	 * @param o The object to which this Award is being compared
	 * @return True if the Awards have the same name, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Award)) {
			return false;
		}
		Award other = (Award) o;
		return this.name.equals(other.name);
	}
}
