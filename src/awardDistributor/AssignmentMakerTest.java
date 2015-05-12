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
		//TODO Test findOptimalAssignments method
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindMaximumMatching() {
		
		//TODO Fix findMaximumMatching method

		// First test: direct matching (1 to 1, 2 to 2, 3 to 3)
		int[][] rankingMatrix1 = {{0, 1, 3},
								  {1, 0, 2},
								  {1, 1, 0}};
		
		int[][] adjMatrix1 = AssignmentMaker.generateAdjacencyMatrix(rankingMatrix1);
		
		int[][] expectedFN1 = {{0, 1, 1, 1, 0, 0, 0, 0},
		 	   				   {0, 0, 0, 0, 1, 0, 0, 0},
		 	   				   {0, 0, 0, 0, 0, 1, 0, 0},
							   {0, 0, 0, 0, 0, 0, 1, 0},
							   {0, 0, 0, 0, 0, 0, 0, 1},
							   {0, 0, 0, 0, 0, 0, 0, 1},
							   {0, 0, 0, 0, 0, 0, 0, 1},
							   {0, 0, 0, 0, 0, 0, 0, 0}};
		
		assertArrayEquals(expectedFN1, AssignmentMaker.findMaximumMatching(
															rankingMatrix1, adjMatrix1));
		
		// Second test: imperfect matching (1 to 1, 2 to 2)
		int[][] rankingMatrix2 = {{0, 1, 3},
								  {0, 0, 0},
								  {0, 1, 2}};

		int[][] adjMatrix2 = AssignmentMaker.generateAdjacencyMatrix(rankingMatrix2);
		
		int[][] expectedFN2 = {{0, 1, 1, 0, 0, 0, 0, 0},
			 				   {0, 0, 0, 0, 1, 0, 0, 0},
			 				   {0, 0, 0, 0, 0, 1, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 1},
							   {0, 0, 0, 0, 0, 0, 0, 1},
							   {0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0}};

		assertArrayEquals(expectedFN2, AssignmentMaker.findMaximumMatching(
															rankingMatrix2, adjMatrix2));
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
		int[][] flowNetwork = {{0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0},
							   {0, 0, 0, 0, 0, 0, 0, 0}};
		
		int[][] resGraph = {{0, 1, 1, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 1, 0, 0, 0},
							{0, 0, 0, 0, 0, 1, 0, 0},
							{0, 0, 0, 0, 0, 0, 1, 0},
							{0, 0, 0, 0, 0, 0, 0, 1},
							{0, 0, 0, 1, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 1},
							{0, 0, 0, 0, 0, 0, 0, 0}};
		
		int numNoms = 3;
		
		// First test: path goes source to 1 to 4 to sink
		Path p1 = new Path(resGraph, numNoms);
		
		p1.moveForward();
		p1.moveForward();
		p1.moveForward();
		
		assertEquals(Path.Status.COMPLETE_PATH, p1.status);
		
		AssignmentMaker.updateGraphs(flowNetwork, resGraph, p1);
		
		int[][] expectedFlowNetwork1 = {{0, 1, 0, 0, 0, 0, 0, 0},
								 	   	{0, 0, 0, 0, 1, 0, 0, 0},
 									    {0, 0, 0, 0, 0, 0, 0, 0},
									    {0, 0, 0, 0, 0, 0, 0, 0},
									    {0, 0, 0, 0, 0, 0, 0, 1},
									    {0, 0, 0, 0, 0, 0, 0, 0},
									    {0, 0, 0, 0, 0, 0, 0, 0},
									    {0, 0, 0, 0, 0, 0, 0, 0}};

		assertArrayEquals(expectedFlowNetwork1, flowNetwork);
		
		int[][] expectedResGraph1 = {{0, 0, 1, 0, 0, 0, 0, 0},
									 {1, 0, 0, 0, 0, 0, 0, 0},
									 {0, 0, 0, 0, 0, 1, 0, 0},
									 {0, 0, 0, 0, 0, 0, 1, 0},
									 {0, 1, 0, 0, 0, 0, 0, 0},
									 {0, 0, 0, 1, 0, 0, 0, 0},
									 {0, 0, 0, 0, 0, 0, 0, 1},
									 {0, 0, 0, 0, 1, 0, 0, 0}};
		
		assertArrayEquals(expectedResGraph1, resGraph);
		
		// Second test: path goes source to 2 to 5 to 3 to 6 to sink
		Path p2 = new Path(resGraph, numNoms);
		
		p2.moveForward();
		p2.moveForward();
		p2.moveBackward();
		p2.moveForward();
		p2.moveForward();
		
		assertEquals(Path.Status.COMPLETE_PATH, p2.status);
		
		AssignmentMaker.updateGraphs(flowNetwork, resGraph, p2);
		
		int[][] expectedFlowNetwork2 = {{0, 1, 1, 0, 0, 0, 0, 0},
								 	   	{0, 0, 0, 0, 1, 0, 0, 0},
 									    {0, 0, 0, 0, 0, 1, 0, 0},
									    {0, 0, 0, 0, 0, 0, 1, 0},
									    {0, 0, 0, 0, 0, 0, 0, 1},
									    {0, 0, 0, 1, 0, 0, 0, 0},
									    {0, 0, 0, 0, 0, 0, 0, 1},
									    {0, 0, 0, 0, 0, 0, 0, 0}};

		assertArrayEquals(expectedFlowNetwork2, flowNetwork);
		
		int[][] expectedResGraph2 = {{0, 0, 0, 0, 0, 0, 0, 0},
									 {1, 0, 0, 0, 0, 0, 0, 0},
									 {1, 0, 0, 0, 0, 0, 0, 0},
									 {0, 0, 0, 0, 0, 1, 0, 0},
									 {0, 1, 0, 0, 0, 0, 0, 0},
									 {0, 0, 1, 0, 0, 0, 0, 0},
									 {0, 0, 0, 1, 0, 0, 0, 0},
									 {0, 0, 0, 0, 1, 0, 1, 0}};

		assertArrayEquals(expectedResGraph2, resGraph);
	}
	
	@Test
	public void testAdjustWeights() {
		//TODO Test adjustWeights method
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindMinCover() {
		//TODO Test findMinCover method
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindUnmatchedAwards() {
		//TODO Test findUnmatchedAwards method
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindSetZ() {
		//TODO Test findSetZ method
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddAlternating() {
		//TODO Test addAlternating method
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindMatrixMin() {
		//TODO Test findMatrixMin method
		fail("Not yet implemented");
	}
}
