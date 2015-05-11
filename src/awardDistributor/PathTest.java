package awardDistributor;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class PathTest {

	int[][] resGraph;
	int numNoms;
	
	@Before
	public void setUp() throws Exception {
		int[][] resGraph = {{0, 1, 1, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 1, 1, 1, 0},
							{0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 1, 1, 0},
							{0, 0, 0, 1, 0, 0, 0, 1},
							{0, 1, 0, 1, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 1},
							{0, 0, 0, 0, 0, 0, 0, 0}};
		
		this.resGraph = resGraph;
		
		numNoms = 3;
	}

	@Test
	public void testMoveForward() {
		// First test: path goes from source to sink in 3 steps
		Path p1 = new Path(resGraph, numNoms);
		LinkedList<Integer> expected1 = new LinkedList<Integer>();
		expected1.add(0);
		
		assertTrue(expected1.equals(p1.vertices));
		assertEquals(0, p1.current);
		assertEquals(Path.Status.AT_SOURCE, p1.status);
		
		assertTrue(p1.moveForward());
		expected1.add(1);
		
		assertTrue(expected1.equals(p1.vertices));
		assertEquals(1, p1.current);
		assertEquals(Path.Status.AT_AWARDS, p1.status);
		
		assertTrue(p1.moveForward());
		expected1.add(4);
		
		assertTrue(expected1.equals(p1.vertices));
		assertEquals(4, p1.current);
		assertEquals(Path.Status.AT_NOMINEES, p1.status);
		
		assertTrue(p1.moveForward());
		expected1.add(p1.sink);
		
		assertTrue(expected1.equals(p1.vertices));
		assertEquals(p1.sink, p1.current);
		assertEquals(Path.Status.COMPLETE_PATH, p1.status);
		
		assertFalse(p1.moveForward());
		
		assertTrue(expected1.equals(p1.vertices));
		assertEquals(p1.sink, p1.current);
		assertEquals(Path.Status.COMPLETE_PATH, p1.status);
		
		resGraph[0][1] = 0;
		
		// Second test: residual graph is changed to path can't progress beyond awards
		Path p2 = new Path(resGraph, numNoms);
		LinkedList<Integer> expected2 = new LinkedList<Integer>();
		expected2.add(0);
		
		assertTrue(expected2.equals(p2.vertices));
		assertEquals(0, p2.current);
		assertEquals(Path.Status.AT_SOURCE, p2.status);
		
		assertTrue(p2.moveForward());
		expected2.add(2);
		
		assertTrue(expected2.equals(p2.vertices));
		assertEquals(2, p2.current);
		assertEquals(Path.Status.AT_AWARDS, p2.status);
		
		assertFalse(p1.moveForward());
		
		assertTrue(expected2.equals(p2.vertices));
		assertEquals(2, p2.current);
		assertEquals(Path.Status.AT_AWARDS, p2.status);
	}

	@Test
	public void testMoveBackward() {
		// First test: successful backwards move
		Path p1 = new Path(resGraph, numNoms);
		LinkedList<Integer> expected1 = new LinkedList<Integer>();
		expected1.add(0);
		
		p1.moveForward();
		expected1.add(1);
		p1.moveForward();
		expected1.add(4);
		
		assertTrue(p1.moveBackward());
		expected1.add(3);
		
		assertTrue(expected1.equals(p1.vertices));
		assertEquals(3, p1.current);
		assertEquals(Path.Status.AT_AWARDS, p1.status);
		
		assertFalse(p1.moveBackward());
		
		assertTrue(expected1.equals(p1.vertices));
		assertEquals(3, p1.current);
		assertEquals(Path.Status.AT_AWARDS, p1.status);
		
		resGraph[4][3] = 0;
		
		//Second test: unsuccessful backwards move
		Path p2 = new Path(resGraph, numNoms);
		LinkedList<Integer> expected2 = new LinkedList<Integer>();
		expected2.add(0);
		
		p2.moveForward();
		expected2.add(1);
		p2.moveForward();
		expected2.add(4);
		
		assertFalse(p2.moveBackward());
		
		assertTrue(expected2.equals(p2.vertices));
		assertEquals(4, p2.current);
		assertEquals(Path.Status.AT_NOMINEES, p2.status);
	}

	@Test
	public void testBacktrack() {
		Path p1 = new Path(resGraph, numNoms);
		LinkedList<Integer> expected = new LinkedList<Integer>();
		expected.add(0);
		
		assertTrue(expected.equals(p1.vertices));
		assertEquals(0, p1.current);
		
		p1.moveTo(2);
		expected.add(2);
		
		assertTrue(expected.equals(p1.vertices));
		assertEquals(2, p1.current);
		
		p1.backtrack();
		expected.removeLast();
		
		assertTrue(expected.equals(p1.vertices));
		assertEquals(0, p1.current);
		
		p1.moveTo(1);
		expected.add(1);
		p1.moveTo(5);
		expected.add(5);
		p1.backtrack();
		expected.removeLast();
		
		assertTrue(expected.equals(p1.vertices));
		assertEquals(1, p1.current);
	}
	
	@Test
	public void testMoveTo() {
		Path p1 = new Path(resGraph, numNoms);
		LinkedList<Integer> expected = new LinkedList<Integer>();
		expected.add(0);
		
		assertEquals(p1.current, 0);
		assertEquals(p1.vertices.size(), 1);
		
		p1.moveTo(2);
		expected.add(2);
		
		assertEquals(p1.current, 2);
		assertEquals(p1.vertices.size(), 2);
		
		p1.moveTo(0);
		expected.add(0);
		
		assertEquals(p1.current, 0);
		assertEquals(p1.vertices.size(), 3);
		
		p1.moveTo(1);
		expected.add(1);
		
		assertEquals(p1.current, 1);
		assertEquals(p1.vertices.size(), 4);
		
		p1.moveTo(4);
		expected.add(4);
		
		assertEquals(p1.current, 4);
		assertEquals(p1.vertices.size(), 5);
		
		p1.moveTo(p1.sink);
		expected.add(p1.sink);
		
		assertEquals(p1.current, p1.sink);
		assertTrue(expected.equals(p1.vertices));
	}
	
	@Test
	public void testParseEdges() {
		Path p1 = new Path(resGraph, numNoms);
		p1.moveForward();
		p1.moveForward();
		
		assertTrue(p1.hasNextEdge());
		assertEquals(p1.firstIndex(), 0);
		assertEquals(p1.secondIndex(), 1);
		
		assertTrue(p1.hasNextEdge());
		assertEquals(p1.firstIndex(), 1);
		assertEquals(p1.secondIndex(), 4);
		
		assertFalse(p1.hasNextEdge());
	}

}
