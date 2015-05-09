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
		boolean isPerfectMatching = false;
		
		while(!isPerfectMatching) {
			
			rowSubtractMin(rankingMatrix); // subtract minimum from each row
			colSubtractMin(rankingMatrix); // subtract minimum from each column
			
			int[][] flowNetwork = findMaximumMatching(rankingMatrix);
		}
		
		return null;
	}
	
	/**
	 * Subtracts the minimum element in the row from every element in the row.
	 * 
	 * @param matrix A 2D array of ints
	 */
	static void rowSubtractMin(int[][] matrix) {
		for (int[] row : matrix) {
			int min = findMin(row);
			for (int col = 0; col < row.length; col++) row[col] -= min;
		}
	}
	
	/**
	 * Subtracts the minimum element in the column from every element in
	 * the column.
	 * 
	 * @param matrix A 2D array of ints
	 */
	static void colSubtractMin(int[][] matrix) {
		for (int col = 0; col < matrix[0].length; col++) {
			int min = findMin(matrix, col);
			for (int row = 0; row < matrix.length; row++) matrix[row][col] -= min;
		}
	}
	
	/**
	 * Returns the flow network that is generated when we use a variation
	 * of the Ford-Fulkerson Max-Flow algorithm to find the maximum
	 * matching of awards and nominees. In developing this code, I used 
	 * <a href="https://www.youtube.com/watch?v=hmIrJCGPPG4">this Youtube tutorial</a>
	 * in order to understand and adapt the algorithm for this project.
	 * 
	 * @param matrix Ranking matrix of awards and nominees
	 * @return The flow network generated
	 */
	static int[][] findMaximumMatching(int[][] rankingMatrix) {
		int numNoms = rankingMatrix.length;
		
		int[][] flowNetwork = new int[2 * numNoms + 2][2 * numNoms + 2];
		int[][] adjMatrix = generateAdjacencyMatrix(rankingMatrix);
		int[][] residualGraph = adjMatrix.clone();
		
		return null;
	}
	
	/**
	 * Returns an adjacency matrix for a directed graph that consists of a 
	 * source vertex, a column of award vertices, a column of nominee
	 * vertices, and a sink vertex. The source vertex has an edge to every
	 * award vertex, and the sink vertex has an incoming edge from every
	 * nominee vertex. The input ranking matrix determine the edges in the
	 * middle; if there is a zero for the ith award and jth nominee, then
	 * the ith award vertex has an edge to the jth nominee vertex.
	 * 
	 * @param rankingMatrix An 2D array of award-nominee rankings
	 * @return A 2D array representing an adjacency matrix
	 */
	static int[][] generateAdjacencyMatrix(int[][] rankingMatrix) {
		int numNoms = rankingMatrix.length;
		
		int[][] adjMatrix = new int[2 * numNoms + 2][2 * numNoms + 2];
		
		// Connect source vertex to all award vertices
		for (int i = 1; i < numNoms + 1; i++) adjMatrix[0][i] = 1;
		
		// Connect all nominee vertices to sink vertex
		int sinkIndex = 2 * numNoms + 1;
		for (int i = numNoms + 1; i < sinkIndex; i++) adjMatrix[i][sinkIndex] = 1;
		
		// Connect award vertices to nominee vertices where the ranking matrix is 0
		for (int a = 0; a < numNoms; a++) {
			for (int n = 0; n < numNoms; n++) {
				if (rankingMatrix[a][n] == 0) adjMatrix[a + 1][n + 1 + numNoms] = 1;
			}
		}
		
		return adjMatrix;
	}
	
	/**
	 * Returns the minimum element in the input row.
	 * 
	 * @param row An array of ints
	 * @return The minimum of the array
	 */
	static int findMin(int[] row) {
		int min = Integer.MAX_VALUE;
		for (int elem : row) {
			min = Math.min(min, elem);
		}
		return min;
	}
	
	/**
	 * Returns the minimum element in the specified column of the input matrix
	 * 
	 * @param matrix A 2D array of ints
	 * @param col The index of the specified column
	 * @return The minimum of the column
	 */
	static int findMin(int[][] matrix, int col) {
		int min = Integer.MAX_VALUE;
		for (int row = 0; row < matrix.length; row++) {
			min = Math.min(min, matrix[row][col]);
		}
		return min;
	}
}
