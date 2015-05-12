package awardDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;


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
	
	static int numNoms; // total number of nominees
	static int sinkIndex; // index of the sink vertex in the constructed graph
	static int awardOffset; // offset between award index and its index in the flow network
	static int nomOffset; // offset between nominee index and its index in the flow network
	
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
		numNoms = rankingMatrix.length;
		sinkIndex = 2 * numNoms + 1;
		awardOffset = 1;
		nomOffset = numNoms + 1;
		
		while(true) {
			
			rowSubtractMin(rankingMatrix); // subtract minimum from each row
			colSubtractMin(rankingMatrix); // subtract minimum from each column
			
			int[][] adjMatrix = generateAdjacencyMatrix(rankingMatrix);
			HashMap<Integer, Integer> matching = findMaximumMatching(rankingMatrix,
																				adjMatrix);
			// If the matching is perfect, return it
			if (matching.size() == numNoms) return matching;
			
			// Otherwise, adjust the ranking matrix weights, according to the algorithm
			adjustWeights(rankingMatrix, adjMatrix, matching);
		}
	}
	
	/**
	 * Returns a HashMap representing the matching (where key:award and
	 * value:nominee) that is generated when we use a variation
	 * of the Ford-Fulkerson Max-Flow algorithm to find the maximum
	 * matching of awards and nominees. In developing this code, I used 
	 * <a href="https://www.youtube.com/watch?v=hmIrJCGPPG4">this Youtube tutorial</a>
	 * in order to understand and adapt the algorithm for this project.
	 * 
	 * @param rankingMatrix Ranking matrix of awards and nominees
	 * @param adjMatrix Adjancency matrix for graph of 0-weight edges
	 * @return A HashMap representing the maximum matching
	 */
	static HashMap<Integer, Integer> findMaximumMatching(int[][] rankingMatrix,
																int[][] adjMatrix) {
		int[][] flowNetwork = new int[2 * numNoms + 2][2 * numNoms + 2];
		int[][] resGraph = adjMatrix.clone(); // "residual graph"
		
		Path path = new Path(resGraph, numNoms);
		
		// Loop while there still exists an augmenting path
		while (true) {
			
			// Try to move forward
			if(path.moveForward()) {
				
				// If successful, check if the path reaches the sink
				if (path.status == Path.Status.COMPLETE_PATH) {	
					updateGraphs(flowNetwork, resGraph, path);
					path = new Path(adjMatrix, numNoms); // begin a new path
				}
				continue;
			}
			
			// Can't move forward - check if we're stuck at the source
			if (path.status == Path.Status.AT_SOURCE) {
				break; // no more augmenting paths
			}
			
			// Otherwise, if the path is at the nominee level, try to move backwards
			if (path.status == Path.Status.AT_NOMINEES) {
				if (path.moveBackward()) {
					continue;
				}
			}
			
			// If all of the above fails, retreat to the previous vertex
			path.backtrack();
		}
		
		return convertToMatching(flowNetwork);
	}
	
	/**
	 * Interprets the flow network to return the matching represented
	 * by the flow network, with the indices of the awards and nominees
	 * corrected so that they reflect the indices of within the group of
	 * awards and nominees, respectively.
	 * 
	 * @param flowNetwork A 2D array representing a flow network
	 * @return A HashMap representing the matching
	 */
	static HashMap<Integer, Integer> convertToMatching(int[][] flowNetwork) {
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		
		// Look through all possible award-nominee edges
		for (int v1 = 1; v1 < numNoms + 1; v1++) {
			for (int v2 = numNoms + 1; v2 < sinkIndex; v2++) {
				if (flowNetwork[v1][v2] == 1) {
					// If an edge exists, add the pairing's indices to the result
					result.put(v1 - awardOffset, v2 - nomOffset);
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Given a complete path from source to sink, this method updates the
	 * flow network and the residual graph to reflect the addition of this
	 * augmenting path.
	 * 
	 * @param flowNetwork The 2D array representing the flow network
	 * @param resGraph The 2D array representing the residual graph
	 * @param path An augmenting path
	 */
	static void updateGraphs(int[][] flowNetwork, int[][] resGraph, Path path) {
		while (path.hasNextEdge()) {
			int v1 = path.firstIndex(); // This edge comes out of v1
			int v2 = path.secondIndex(); // ...and goes into v2
			
			// Add this edge to the flow network (or remove its back-edge counterpart)
			if (flowNetwork[v2][v1] == 1) flowNetwork[v2][v1] = 0;
			else flowNetwork[v1][v2] = 1;
			
			// Remove this edge from the residual graph and add a back-edge in its place
			resGraph[v1][v2] = 0;
			resGraph[v2][v1] = 1;
		}
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
				
		int[][] adjMatrix = new int[2 * numNoms + 2][2 * numNoms + 2];
		
		// Connect source vertex to all award vertices
		for (int i = 1; i < numNoms + 1; i++) adjMatrix[0][i] = 1;
		
		// Connect all nominee vertices to sink vertex
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
	 * Adjusts the weights of the ranking matrix by finding the minimum
	 * cover of the graph with 0-weight edges as well as the non-zero minimum
	 * value of the ranking matrix, and adjusting the values in the ranking
	 * matrix according to the Hungarian Algorithm.
	 * 
	 * @param rankingMatrix The ranking matrix to be adjusted
	 * @param adjMatrix The adjacency matrix for the graph of 0-weight edges
	 * @param matching A HashMap representing a bipartite graph matching
	 */
	static void adjustWeights(int[][] rankingMatrix, int[][] adjMatrix,
													HashMap<Integer, Integer> matching) {
		// Find the minimum vertex cover
		HashSet<Integer> minCover = findMinCover(matching, adjMatrix);
		
		// Find the non-zero minimum of the ranking matrix
		int min = findMatrixMin(rankingMatrix);
		
		// For all possible pairs of awards and nominees...
		for (int v1 = 0; v1 < numNoms; v1++) {
			int v_1 = v1 + 1; // award index in flow network graph
			for (int v2 = 0; v2 < numNoms; v2++) {
				int v_2 = v2 + numNoms + 1; // nominee index in flow network graph
				
				if (!minCover.contains(v_1) && !minCover.contains(v_2)) {
					// If neither v1 nor v2 is in the min cover, subtract the min
					rankingMatrix[v1][v2] -= min;
					
				} else if (minCover.contains(v_1) && minCover.contains(v_2)) {
					// If both v1 and v2 are in the min cover, add the min
					rankingMatrix[v1][v2] += min;
				}
			}
		}
	}
	
	/**
	 * Returns the non-zero minimum value of all the elements in the 2D array.
	 * 
	 * @param matrix A 2D array of ints
	 * @return The non-zero minimum value of the 2D array
	 */
	static int findMatrixMin(int[][] matrix) {
		int min = Integer.MAX_VALUE;
		
		for (int row = 0; row < numNoms; row++) {
			for (int col = 0; col < numNoms; col++) {
				if (matrix[row][col] < min && matrix[row][col] != 0) min = matrix[row][col];
			}
		}
		
		return min;
	}
	
	/**
	 * Returns a HashSet of all the vertices that are part of a minimum vertex
	 * cover. The algorithm implemented in this method is derived from the
	 * <a href="http://en.wikipedia.org/wiki/König's_theorem_(graph_theory)#Algorithm">Wikipedia page of Konig's Theorem</a>.
	 * 
	 * @param matching A HashMap representing a matching of awards and nominees
	 * @param adjMatrix An adjacency matrix of 0-weight edges
	 * @return A HashSet of the vertices that comprise the minimum cover
	 */
	static HashSet<Integer> findMinCover(HashMap<Integer, Integer> matching,
																	int[][] adjMatrix) {
		// Set U consists of all awards that are not part of the matching
		HashSet<Integer> u = findUnmatchedAwards(matching);
		
		// Set Z consists of U and all vertices reachable from U via alternating paths
		// (paths that alternate between matched and unmatched edges)
		HashSet<Integer> z = findSetZ(matching, adjMatrix, u);
		
		HashSet<Integer> minCover = new HashSet<Integer>();
		
		// The minimum cover is (Awards - Z) union (Nominees intersect Z)
		for (int v = 1; v < numNoms + 1; v++) {
			if (!z.contains(v)) minCover.add(v);
		}
		for (int v = numNoms + 1; v < sinkIndex; v++) {
			if (z.contains(v)) minCover.add(v);
		}
		
		return minCover;
	}
	
	/**
	 * Returns a set of all of the vertices in U as well as all the vertices
	 * that are reachable from U via an alternating path. An alternating path
	 * consists of edges that alternate between being a part of the matching
	 * and not being included in the matching.
	 * 
	 * @param matching A HashMap representing pairs of matched vertices
	 * @param adjMatrix A 2D array representing the graph of 0-weight edges
	 * @param u A HashSet representing the set U
	 * @return A HashSet that represents the set Z
	 */
	static HashSet<Integer> findSetZ(HashMap<Integer, Integer> matching,
												int[][] adjMatrix, HashSet<Integer> u) {
		
		// A bidirectional HashMap since we will need to do value-to-key look-ups
		DualHashBidiMap<Integer, Integer> biMatching =
										new DualHashBidiMap<Integer, Integer> (matching);
		
		Iterator uVertices = ((HashSet<Integer>) u.clone()).iterator();
		
		// All vertices in U are in Z
		HashSet<Integer> z = u;
		
		// Add vertices reachable from U via alternating paths
		while(uVertices.hasNext()) {
			addAlternating((int) uVertices.next(), z, biMatching, adjMatrix);
		}
		
		return z;
	}
	
	/**
	 * Adds all of the edges that are reachable from input vertex v via a
	 * path that consists of edges that alternate between being included
	 * in the given matching and those that are not included in the matching.
	 * 
	 * @param v The vertex from which to begin the path
	 * @param z The current state of set Z
	 * @param matching A HashMap representing the matching
	 * @param adjMatrix A 2D array representing the graph of 0-weight edges
	 */
	static void addAlternating(int v, HashSet<Integer> z, 
						DualHashBidiMap<Integer, Integer> matching, int[][] adjMatrix) {
		if (v >= 1 && v <= numNoms) {
			// If v is an award vertex, look for an unmatched edge to a nominee vertex
			int[] edges = adjMatrix[v];
			
			for (int v2 = numNoms + 1; v2 < sinkIndex; v2++) {
				if (edges[v2] == 1 && !z.contains(v2)) {
					z.add(v2);
					addAlternating(v2, z, matching, adjMatrix); // recursively continue the path
				}
			}
			
		} else if (v >= numNoms + 1 && v <= 2 * numNoms) {
			// If v is a nominee vertex, look for an matched edge to an award vertex
			if (matching.containsValue(v - nomOffset)) {
				int v1 = matching.getKey(v - nomOffset) + awardOffset;
				if (!z.contains(v1)) {
					z.add(v1);
					addAlternating(v1, z, matching, adjMatrix); // recursively continue the path
				}
			}
		}
	}
	
	/**
	 * Returns a HashSet of all the award vertices that are not a part of the
	 * matching. 
	 * 
	 * @param matching A HashMap representing a matching
	 * @return A HashSet that includes all unmatched award vertices
	 */
	static HashSet<Integer> findUnmatchedAwards(HashMap<Integer, Integer> matching) {
		HashSet<Integer> result = new HashSet<Integer>();
		
		for (int v = 1; v < numNoms + 1; v++) {
			if (!matching.containsKey(v - awardOffset)) result.add(v);
		}
		
		return result;
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
}
