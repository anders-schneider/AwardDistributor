package awardDistributor;

/**
 * The Award class represents an award that can have multiple nominees
 * but only one recipient.
 * 
 * @author Anders Schneider
 *
 */

public class Award {
	
	private static int numAwards = 0; // total number of awards created
	
	String name;
	private int index;
	private String[] noms;
	
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
	 * Resets the number of awards to be 0 so that subsequent awards have
	 * indices 0, 1, 2, etc. (For testing purposes only.)
	 */
	public static void resetIndex() {
		numAwards = 0;
	}
	
	/**
	 * Sets the <code>noms</code> instance variable for this Award.
	 * 
	 * @param noms An ordered array of the nominees for this award
	 */
	public void setNoms(String[] noms) {
		
		for (int i = 0; i < noms.length; i++) {
			String s1 = noms[i];
			for (int j = i + 1; j < noms.length; j++) {
				String s2 = noms[j];
				if (s1.equals(s2)) throw new IllegalArgumentException("Same student ranked twice");
			}
		}
		
		this.noms = noms;
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
	 * Returns the ranking of the nominee for this particular award
	 * (1 is the best possible ranking).
	 * 
	 * @param nom A nominee for this Award
	 * @return The ranking of the specified nominee for this Award
	 */
	public int getRanking(String nominee) {
		for (int i = 0; i < noms.length; i++) {
			if (noms[i].equals(nominee)) return i + 1;
		}
		throw new IllegalArgumentException(nominee + " does not appear in the list of nominees for this Award");
	}
	
	/**
	 * Returns <code>true</code> if the award has the input nominee amongst
	 * it ranked list of nominees, <code>false</code> otherwise.
	 * 
	 * @param nominee The name of the nominee to check into
	 * @return A boolean indicating if 
	 */
	public boolean hasNom(String nominee) {
		for (int i = 0; i < noms.length; i++) {
			if (nominee.equals(noms[i])) return true;
		}
		return false;
	}
	
	/**
	 * Returns the hashcode corresponding to this Award.
	 * 
	 * @return The hashcode corresponding to this Award.
	 */
	@Override
	public int hashCode() {
		return name.hashCode() + index;
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
		return this.name.equals(other.name) && this.index == other.getIndex();
	}
}
