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
	int current; // Vertex most recently added to the path
	boolean[] visited; // Array that indicates which vertices were already visited
	int[][] resGraph; // Residual graph
	int numNoms; // Number of nominees
	
	final int sink; // Index of the sink vertex
	
	/**
	 * These Statuses refer to where the AssignmentMaker is in the process
	 * of traversing the constructed graph to find the maximum flow.
	 */
	public enum Status {AT_SOURCE, AT_AWARDS, AT_NOMINEES, COMPLETE_PATH;}
	
	/**
	 * Constructor sets the status to AT_SOURCE and makes a note that the
	 * source vertex is the only vertex visited so far.
	 */
	public Path(int[][] resGraph, int numNoms) {
		// The path begins at the source vertex (index 0)
		status = Status.AT_SOURCE;
		vertices = new LinkedList<Integer>();
		vertices.add(0);
		current = 0;
		
		// The graph begins with only the source vertex as "visited"
		visited = new boolean[2 * numNoms + 2];
		Arrays.fill(visited, false);
		visited[0] = true;
		this.resGraph = resGraph;
		this.numNoms = numNoms;
		
		sink = 2 * numNoms + 1;
	}
	
	/**
	 * Searches for a move forward (in the direction from source to sink)
	 * and returns <code>true</code> if it finds such a move and 
	 * <code>false</code> otherwise. If a successful move forward is possible,
	 * the path is updated to reflect this move.
	 * 
	 * @return <code>true</code> if a move forward exists; <code>false</code> otherwise
	 */
	boolean moveForward() {		
		
		if (status == Status.AT_SOURCE) {
			
			// Try to move from source to awards level
			for (int i = 1; i < numNoms + 1; i++) {
				if (!visited[i] && resGraph[current][i] == 1) {
					moveTo(i);
					status = Status.AT_AWARDS;
					return true;
				}
			}
		} else if (status == Status.AT_AWARDS) {
			
			// Try to move from awards level to nominee level
			for (int i = numNoms + 1; i < sink; i++) {
				if (!visited[i] && resGraph[current][i] == 1) {
					moveTo(i);
					status = Status.AT_NOMINEES;
					return true;
				}
			}
		} else if (status == Status.AT_NOMINEES) {
			
			// Try to move from nominee level to sink
			if (resGraph[current][sink] == 1) {
				moveTo(sink);
				status = Status.COMPLETE_PATH;
				return true;
			}
		}
		
		// Otherwise: no way to continue the path forward
		return false;
	}
	
	/**
	 * Updates the path to reflect a move to the input vertex.
	 * 
	 * @param i The index of the next vertex to be included in the path
	 */
	void moveTo(int i) {
		current = i;
		visited[i] = true;
		vertices.add(current);
	}
	
	/**
	 * Searches for a move backward (in the direction from the sink to the
	 * source) and returns <code>true</code> if it finds such a move and
	 * <code>false</code> otherwise. If a successful move backward is possible,
	 * the path is updated to reflect this move.
	 * 
	 * @return <code>true</code> if a move backward exists; <code>false</code> otherwise
	 */
	boolean moveBackward() {
		// If the path is at the source or awards level, it should not try to move backwards
		if (status == Status.AT_SOURCE || status == Status.AT_AWARDS) return false;
		
		// Searches for a move backward from nominee to award level
		for (int i = 1; i < numNoms + 1; i++) {
			if (!visited[i] && resGraph[current][i] == 1) {
				moveTo(i);
				status = Status.AT_AWARDS;
				return true;
			}
		}
		
		// Otherwise, no backward move possible
		return false;
	}
	
	/**
	 * Steps back one step in the path: removes the last vertex from the path
	 * and returns to the previous vertex.
	 */
	void backtrack() {
		vertices.removeLast(); // Remove the last step (but it stays "visited")
		current = vertices.getLast(); // Go back to previous last step
		
		// Update the path status
		if (current == 0) status = Status.AT_SOURCE;
		else if (current < numNoms + 1) status = Status.AT_AWARDS;
		else if (current < sink) status = Status.AT_NOMINEES;
	}
	
	/**
	 * Returns <code>true</code> if the path has another edge, and
	 * <code>false</code> otherwise.
	 * 
	 * @return A boolean indicating if the path has another edge
	 */
	boolean hasNextEdge() {
		return vertices.size() >= 2;
	}
	
	/**
	 * Returns the index of the first vertex of the next edge and removes
	 * that vertex from the current path.
	 * 
	 * @return The index of the first vertex of the next edge
	 */
	int firstIndex() {
		return vertices.removeFirst();
	}
	
	/**
	 * Returns the index of the second vertex of the next edge
	 * 
	 * @return The index of the second vertex of the next edge
	 */
	int secondIndex() {
		return vertices.getFirst();
	}
}