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
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Award)) {
			return false;
		}
		Award other = (Award) o;
		return this.name.equals(other.name);
	}
}
