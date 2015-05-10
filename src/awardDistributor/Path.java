package awardDistributor;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * The Path class represents a path through the graph represented by the
 * adjacency matrix in the AssignmentMaker. These paths all begin at the
 * source node and attempt to find a way to the sink node (which would
 * make this particular path an "augmenting path").
 * 
 * @author Anders Schneider
 *
 */
public class Path {

	Status status;
	LinkedList<Integer> vertices; // Ordered list of vertices visited
	boolean[] visited; // Array that indicates which vertices were already visited
	int[][] graph;
	
	/**
	 * These Statuses refer to where the AssignmentMaker is in the process
	 * of traversing the constructed graph to find the maximum flow.
	 */
	public enum Status {AT_SOURCE, AT_AWARDS, AT_NOMINEES, COMPLETE_PATH;}
	
	/**
	 * Constructor sets the status to AT_SOURCE and makes a note that the
	 * source vertex is the only vertex visited so far.
	 */
	public Path(int[][] graph) {
		status = Status.AT_SOURCE;
		vertices.add(0);
		Arrays.fill(visited, false);
		visited[0] = true;
		this.graph = graph;
	}
	
	/**
	 * Searches for a move forward (in the direction from source to sink)
	 * and returns <code>true</code> if it finds such a move and 
	 * <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if a move forward exists; <code>false</code> otherwise
	 */
	boolean moveForward() {
		//TODO Implement the moveForward method
		return true;
	}
	
	/**
	 * Searches for a move backward (in the direction from the sink to the
	 * source) and returns <code>true</code> if it finds such a move and
	 * <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if a move backward exists; <code>false</code> otherwise
	 */
	boolean moveBackward() {
		//TODO Implement the moveBackward method
		return true;
	}
	
	/**
	 * Steps back one step in the path: removes the last vertex from the path
	 * and returns to the previous vertex.
	 */
	void backtrack() {
		//TODO Implement the backtrack method
	}
}