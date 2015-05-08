package awardDistributor;

import java.util.HashMap;

/**
 * The AssignmentMaker class bears the brunt of the processing in this
 * project. Using a variation of the Kuhn-Munkres Algorithm (also known
 * as the Hungarian Algorithm), this class solves the assignment problem
 * given a 2D array of rankings. For more information on the assignment
 * problem and/or the Kuhn-Munkres Algorithm, see
 * <a href="http://webcache.googleusercontent.com/search?q=cache:EYqpNAzoHukJ:https://community.topcoder.com/tc%3Fmodule%3DStatic%26d1%3Dtutorials%26d2%3DhungarianAlgorithm+&cd=1&hl=en&ct=clnk&gl=us">Top Coder</a>,
 * a website that I referenced when writing this class.
 * 
 * @author Anders Schneider
 *
 */
public class AssignmentMaker {

	/**
	 * Returns a HashMap that corresponds to the optimal pairings of
	 * award and nominee. The returned HashMap has the format
	 * (key = award index, value = nominee index). This method takes a
	 * ranking matrix as input.
	 * 
	 * @param rankingMatrix 2D Array (awards = rows, nominees = columns) of rankings
	 * @return HashMap with the key Award index and the value nominee index
	 */
	public static HashMap<Integer, Integer> findOptimalAssignments(int[][] rankingMatrix) {
		return null;
	}
	
}
