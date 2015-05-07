package awardDistributor;

/**
 * The AwardDistributor class determines the optimal distribution of a number
 * of awards based on ranked lists of nominees for each award. Nominees may be
 * considered for multiple awards, but each nominee may receive only one award.
 * The optimal distribution of awards is one that selects the nominees that are
 * most highly ranked for each award, avoiding duplicates. It may be the case
 * that multiple solutions are determined to be optimal; in such cases, one of
 * these solutions will be arbitrarily selected.
 * 
 * @author Anders Schneider
 *
 */

public class AwardDistributor {
	
	private Award[] awardList;

	public AwardDistributor(Award[] awardList) {
		this.awardList = awardList;
	}
}
