package awardDistributor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AssignmentMakerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOptimalAssignments() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindMaximumMatching() {
		fail("Not yet implemented");
	}

	@Test
	public void testRowSubtractMin() {
		int[][] matrix = {{1,  2, 3},
				  		  {5,  4, 3},
				  		  {7, -3, 3}};

		int[][] expected = {{0,  1, 2},
				  			{2,  1, 0},
				  			{10, 0, 6}};
		
		AssignmentMaker.rowSubtractMin(matrix);
		assertArrayEquals(matrix, expected);
	}

	@Test
	public void testColSubtractMin() {
		int[][] matrix = {{1,  2, 3},
				  		  {5,  4, 3},
				  		  {7, -3, 3}};

		int[][] expected = {{0, 5, 0},
				  			{4, 7, 0},
				  			{6, 0, 0}};
		
		AssignmentMaker.colSubtractMin(matrix);
		assertArrayEquals(matrix, expected);
	}

	@Test
	public void testFindMinRow() {
		int[] arr1 = {2, 4, 1, 9, 13, 5};
		int[] arr2 = {3, 3, 3};
		int[] arr3 = {4, -2, 7, 23};
		
		assertEquals(1, AssignmentMaker.findMin(arr1));
		assertEquals(3, AssignmentMaker.findMin(arr2));
		assertEquals(-2, AssignmentMaker.findMin(arr3));
	}

	@Test
	public void testFindMinColumn() {
		int[][] matrix = {{1,  2, 3},
						  {5,  4, 3},
						  {7, -3, 3}};
		
		assertEquals(1, AssignmentMaker.findMin(matrix, 0));
		assertEquals(-3, AssignmentMaker.findMin(matrix, 1));
		assertEquals(3, AssignmentMaker.findMin(matrix, 2));
	}
	
	@Test
	public void testGenerateAdjacencyMatrix() {
		int[][] rankingMatrix1 = {{0, 5, 0},
	  							  {4, 7, 0},
	  							  {6, 0, 0}};
		
		int[][] expected1 = {{0, 1, 1, 1, 0, 0, 0, 0},
							 {0, 0, 0, 0, 1, 0, 1, 0},
							 {0, 0, 0, 0, 0, 0, 1, 0},
							 {0, 0, 0, 0, 0, 1, 1, 0},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 0}};
		
		assertArrayEquals(AssignmentMaker.generateAdjacencyMatrix(rankingMatrix1), expected1);
		
		int[][] rankingMatrix2 = {{0, 0,  0},
								  {0, 7,  0},
								  {6, 0, -2}};
		
		int[][] expected2 = {{0, 1, 1, 1, 0, 0, 0, 0},
							 {0, 0, 0, 0, 1, 1, 1, 0},
							 {0, 0, 0, 0, 1, 0, 1, 0},
							 {0, 0, 0, 0, 0, 1, 0, 0},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 0}};
		
		assertArrayEquals(AssignmentMaker.generateAdjacencyMatrix(rankingMatrix2), expected2);
	}
	
	@Test
	public void testUpdateGraphs() {
		//TODO Test updateGraphs method
		fail("Not yet implemented");
	}
}
