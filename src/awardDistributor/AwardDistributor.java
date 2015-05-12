package awardDistributor;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	private int nomsPerAward;
	
	/**
	 * Constructor that takes the number of nominations per award as input.
	 * 
	 * @param nomsPerAward The number of nominations per award
	 */
	public AwardDistributor(int nomsPerAward) {
		this.nomsPerAward = nomsPerAward;
	}
	
	/**
	 * Returns a HashMap with the optimal distribution of awards (keys) amongst
	 * the nominees (values).
	 * 
	 * @param awards An array of all awards
	 * @param noms An array of all nominees across all awards
	 * @return A HashMap with the optimal award-nominee pairs
	 */
	public HashMap<Award, String> findOptimalDistribution(Award[] awards, 
														  ArrayList<String> noms) {
		if (awards.length > noms.size()) throw new IllegalArgumentException("More awards than nominees");
		
		int[][] rankingMatrix = generateRankingMatrix(awards, noms);
		
		HashMap<Integer, Integer> assignments = AssignmentMaker.findOptimalAssignments(rankingMatrix);
		
		try {
			HashMap<Award, String> pairings = parsePairings(assignments, awards, noms);
			return pairings;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Too much overlap of nominees for multiple awards");
		}
	}
	
	/**
	 * Returns a HashMap that maps Awards to the Strings that correspond to each
	 * nominee's name. If the algorithm has paired an award to a nominee which that
	 * award did not have on their ranked list, the method throws an
	 * IllegalArgumentException.
	 * 
	 * @param assignments A HashMap mapping award indices to nominee indices
	 * @param awards An array of the awards
	 * @param noms An array of the nominees
	 * @return A HashMap that maps Awards to their chosen Strings (nominees)
	 */
	HashMap<Award, String> parsePairings(HashMap<Integer, Integer> assignments,
												Award[] awards, ArrayList<String> noms) {
		HashMap<Award, String> result = new HashMap<Award, String>();
		
		for (int a = 0; a < awards.length; a++) {
			int nomIndex = assignments.get(a);
			String nominee = noms.get(nomIndex);
			Award award = awards[a];
			
			// If the nominee was not on the award's ranked list, throw an exception
			if (!award.hasNom(nominee)) {
				throw new IllegalArgumentException("Award paired with unranked nominee");
			}
			
			// Otherwise, add the pairing to the result
			result.put(award, nominee);
		}
		
		return result;
	}

	/**
	 * Returns a 2D array of integers. The value in location (i, j) represents 
	 * how highly ranked Nominee j is for Award i. The best possible ranking is
	 * a 1, all other rankings are larger integers. If a nominee is not ranked
	 * for a certain award, the value at position is a large number.
	 * 
	 * @return A 2D array that represents each nominee's ranking for each award
	 */
	int[][] generateRankingMatrix(Award[] awards, ArrayList<String> noms) {
		int numNoms = noms.size();
		int[][] result = new int[numNoms][numNoms];
		
		final int HIGH = numNoms + 100; // "Ranking" for nominees not ranked for an award
		
		// Fill in all spots in the result for which there are rankings
		for (Award award : awards) {
			int awardIndex = award.getIndex();
			for (String nom : award.getNoms()) {
				int nomIndex = noms.indexOf(nom);
				int ranking = award.getRanking(nom);
				result[awardIndex][nomIndex] = ranking;
			}
		}
		
		// Put HIGH in for all nominees not ranked for an award
		for (int row = 0; row < numNoms; row++) {
			for (int col = 0; col < numNoms; col++) {
				if (result[row][col] == 0) result[row][col] = HIGH;
			}
		}
		return result;
	}
	
	/**
	 * Prints a 2D Array to the console (helpful for testing code).
	 * 
	 * @param arr 2D Array to be printed
	 */
	static void print2DArray(int[][] arr) {
		for (int[] row : arr) {
			for (int num : row) {
				System.out.print(num + "\t");
			}
			System.out.println("");
		}
	}
}