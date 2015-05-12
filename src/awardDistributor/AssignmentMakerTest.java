package awardDistributor;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.junit.Before;
import org.junit.Test;

public class AssignmentMakerTest {

	@Before
	public void setUp() throws Exception {
		AssignmentMaker.numNoms = 3;
		AssignmentMaker.awardOffset = 1;
		AssignmentMaker.nomOffset = 4;
		AssignmentMaker.sinkIndex = 7;
	}

	@Test
	public void testFindOptimalAssignments() {
		int[][] rankingMatrix1 = {{1, 4, 5},
								  {5, 7, 6},
								  {5, 8, 8}};
		
		HashMap<Integer, Integer> expected1 = new HashMap<Integer, Integer>();
		expected1.put(0, 1);
		expected1.put(1, 2);
		expected1.put(2, 0);
		
		assertTrue(expected1.equals(AssignmentMaker.findOptimalAssignments(rankingMatrix1)));
		
		int[][] rankingMatrix2 = {{1, 2, 3},
								  {3, 1, 2},
								  {2, 3, 1}};

		HashMap<Integer, Integer> expected2 = new HashMap<Integer, Integer>();
		expected2.put(0, 0);
		expected2.put(1, 1);
		expected2.put(2, 2);
		
		assertTrue(expected2.equals(AssignmentMaker.findOptimalAssignments(rankingMatrix2)));
		
		int[][] rankingMatrix3 = {{1, 9, 3, 2},
								  {1, 2, 9, 3},
								  {2, 1, 9, 3},
								  {9, 9, 9, 9}};

		HashMap<Integer, Integer> expected3 = new HashMap<Integer, Integer>();
		expected3.put(0, 3);
		expected3.put(1, 0);
		expected3.put(2, 1);
		expected3.put(3, 2);
		
		assertTrue(expected3.equals(AssignmentMaker.findOptimalAssignments(rankingMatrix3)));
	}
	
	@Test
	public void testFindMaximumMatching() {

		// First test: direct matching (1 to 1, 2 to 2, 3 to 3)
		int[][] rankingMatrix1 = {{0, 1, 3},
								  {1, 0, 2},
								  {1, 1, 0}};
		
		int[][] adjMatrix1 = AssignmentMaker.generateAdjacencyMatrix(rankingMatrix1);
		
		HashMap<Integer, Integer> expected1 = new HashMap<Integer, Integer>();
		expected1.put(0, 0);
		expected1.put(1, 1);
		expected1.put(2, 2);
		
		assertTrue(expected1.equals(AssignmentMaker.findMaximumMatching(
															rankingMatrix1, adjMatrix1)));
		
		// Second test: imperfect matching (1 to 1, 2 to 2)
		int[][] rankingMatrix2 = {{0, 1, 3},
								  {0, 0, 0},
								  {0, 1, 2}};

		int[][] adjMatrix2 = AssignmentMaker.generateAdjacencyMatrix(rankingMatrix2);
		
		HashMap<Integer, Integer> expected2 = new HashMap<Integer, Integer>();
		expected2.put(0, 0);
		expected2.put(1, 1);
		
		assertTrue(expected2.equals(AssignmentMaker.findMaximumMatching(
															rankingMatrix2, adjMatrix2)));
	}
	
	@Test
	public void testConvertToMatching() {
		
		int[][] flowNetwork1 = {{0, 1, 1, 1, 0, 0, 0, 0},
		 	   				    {0, 0, 0, 0, 1, 0, 0, 0},
		 	   				    {0, 0, 0, 0, 0, 1, 0, 0},
							    {0, 0, 0, 0, 0, 0, 1, 0},
							    {0, 0, 0, 0, 0, 0, 0, 1},
							    {0, 0, 0, 0, 0, 0, 0, 1},
							    {0, 0, 0, 0, 0, 0, 0, 1},
							    {0, 0, 0, 0, 0, 0, 0, 0}};
		
		HashMap<Integer, Integer> matching1 = new HashMap<Integer, Integer>();
		matching1.put(0, 0);
		matching1.put(1, 1);
		matching1.put(2, 2);
		
		assertTrue(matching1.equals(AssignmentMaker.convertToMatching(flowNetwork1)));
		
		int[][] flowNetwork2 = {{0, 1, 1, 0, 0, 0, 0, 0},
				   				{0, 0, 0, 0, 1, 0, 0, 0},
				   				{0, 0, 0, 0, 0, 1, 0, 0},
				   				{0, 0, 0, 0, 0, 0, 0, 0},
				   				{0, 0, 0, 0, 0, 0, 0, 1},
				   				{0, 0, 0, 0, 0, 0, 0, 1},
				   				{0, 0, 0, 0, 0, 0, 0, 0},
				   				{0, 0, 0, 0, 0, 0, 0, 0}};
		
		HashMap<Integer, Integer> matching2 = new HashMap<Integer, Integer>();
		matching2.put(0, 0);
		matching2.put(1, 1);
		
		assertTrue(matching2.equals(AssignmentMaker.convertToMatching(flowNetwork2)));
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
		
		AssignmentMaker.numNoms = 3;
		AssignmentMaker.sinkIndex = 7;
		AssignmentMaker.awardOffset = 1;
		AssignmentMaker.nomOffset = 4;
		
		int[][] rankingMatrix = {{0, 1, 3},
								 {0, 0, 0},
								 {0, 1, 2}};
		
		int[][] adjMatrix = {{0, 1, 1, 1, 0, 0, 0, 0},
							 {0, 0, 0, 0, 1, 0, 0, 0},
							 {0, 0, 0, 0, 1, 1, 1, 0},
							 {0, 0, 0, 0, 1, 0, 0, 0},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 1},
							 {0, 0, 0, 0, 0, 0, 0, 0}};
		
		HashMap<Integer, Integer> matching = new HashMap<Integer, Integer>();
		matching.put(0, 0);
		matching.put(1, 1);
		
		int[][] expected = {{0, 0, 2},
							{1, 0, 0},
							{0, 0, 1}};
		
		AssignmentMaker.adjustWeights(rankingMatrix, adjMatrix, matching);
		
		assertArrayEquals(expected, rankingMatrix);
	}
	
	@Test
	public void testFindMinCover() {

		// Test #1
		AssignmentMaker.numNoms = 7;
		AssignmentMaker.sinkIndex = 15;
		AssignmentMaker.awardOffset = 1;
		AssignmentMaker.nomOffset = 8;
		
		int[][] adjMatrix1 = {{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
		
		HashMap<Integer, Integer> matching1 = new HashMap<Integer, Integer>();
		matching1.put(0, 1);
		matching1.put(1, 3);
		matching1.put(2, 2);
		matching1.put(3, 4);
		matching1.put(4, 5);
		matching1.put(5, 6);
		
		HashSet<Integer> u1 = AssignmentMaker.findUnmatchedAwards(matching1);
		
		HashSet<Integer> expected1 = new HashSet<Integer>();
		expected1.add(3);
		expected1.add(4);
		expected1.add(6);
		expected1.add(9);
		expected1.add(11);
		expected1.add(13);
		
		assertTrue(expected1.equals(AssignmentMaker.findMinCover(matching1, adjMatrix1)));
		
		// Test #2
		AssignmentMaker.numNoms = 4;
		AssignmentMaker.sinkIndex = 9;
		AssignmentMaker.awardOffset = 1;
		AssignmentMaker.nomOffset = 5;
		
		int[][] adjMatrix2 = {{0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 1, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
							  {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
		
		HashMap<Integer, Integer> matching2 = new HashMap<Integer, Integer>();
		matching2.put(0, 2);
		matching2.put(2, 1);
		
		HashSet<Integer> u2 = AssignmentMaker.findUnmatchedAwards(matching2);
		
		HashSet<Integer> expected2 = new HashSet<Integer>();
		expected2.add(1);
		expected2.add(5);
		expected2.add(6);
		expected2.add(8);
		
		assertTrue(expected2.equals(AssignmentMaker.findMinCover(matching2, adjMatrix2)));
	}
	
	@Test
	public void testFindUnmatchedAwards() {
		AssignmentMaker.numNoms = 7;
		AssignmentMaker.sinkIndex = 15;
		AssignmentMaker.awardOffset = 1;
		AssignmentMaker.nomOffset = 8;
		
		HashMap<Integer, Integer> matching1 = new HashMap<Integer, Integer>();
		matching1.put(0, 1);
		matching1.put(1, 3);
		matching1.put(2, 2);
		matching1.put(3, 4);
		matching1.put(4, 5);
		matching1.put(5, 6);
		
		HashSet<Integer> expected1 = new HashSet<Integer>();
		expected1.add(7);
				
		assertTrue(expected1.equals(AssignmentMaker.findUnmatchedAwards(matching1)));
		
		HashMap<Integer, Integer> matching2 = new HashMap<Integer, Integer>();
		matching2.put(0, 6);
		matching2.put(2, 2);
		matching2.put(3, 5);
		
		HashSet<Integer> expected2 = new HashSet<Integer>();
		expected2.add(2);
		expected2.add(5);
		expected2.add(6);
		expected2.add(7);
				
		assertTrue(expected2.equals(AssignmentMaker.findUnmatchedAwards(matching2)));
	}
	
	@Test
	public void testFindSetZ() {
		
		// Test #1
		AssignmentMaker.numNoms = 7;
		AssignmentMaker.sinkIndex = 15;
		AssignmentMaker.awardOffset = 1;
		AssignmentMaker.nomOffset = 8;
		
		int[][] adjMatrix1 = {{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
		
		HashMap<Integer, Integer> matching1 = new HashMap<Integer, Integer>();
		matching1.put(0, 1);
		matching1.put(1, 3);
		matching1.put(2, 2);
		matching1.put(3, 4);
		matching1.put(4, 5);
		matching1.put(5, 6);
		
		HashSet<Integer> u1 = AssignmentMaker.findUnmatchedAwards(matching1);
		
		HashSet<Integer> expected1 = (HashSet<Integer>) u1.clone();
		expected1.add(5);
		expected1.add(2);
		expected1.add(1);
		expected1.add(9);
		expected1.add(11);
		expected1.add(13);
		
		assertTrue(expected1.equals(AssignmentMaker.findSetZ(matching1, adjMatrix1, u1)));
		
		// Test #2
		AssignmentMaker.numNoms = 4;
		AssignmentMaker.sinkIndex = 9;
		AssignmentMaker.awardOffset = 1;
		AssignmentMaker.nomOffset = 5;
		
		int[][] adjMatrix2 = {{0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 1, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
							  {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
		
		HashMap<Integer, Integer> matching2 = new HashMap<Integer, Integer>();
		matching2.put(0, 2);
		matching2.put(2, 1);
		
		HashSet<Integer> u2 = AssignmentMaker.findUnmatchedAwards(matching2);
		
		HashSet<Integer> expected2 = (HashSet<Integer>) u2.clone();
		expected2.add(3);
		expected2.add(5);
		expected2.add(6);
		expected2.add(8);
		
		assertTrue(expected2.equals(AssignmentMaker.findSetZ(matching2, adjMatrix2, u2)));
	}
	
	@Test
	public void testAddAlternating() {
		AssignmentMaker.numNoms = 7;
		AssignmentMaker.sinkIndex = 15;
		AssignmentMaker.awardOffset = 1;
		AssignmentMaker.nomOffset = 8;
		
		int[][] adjMatrix1 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
		
		HashMap<Integer, Integer> matching1 = new HashMap<Integer, Integer>();
		matching1.put(0, 1);
		matching1.put(1, 3);
		matching1.put(2, 2);
		matching1.put(3, 4);
		matching1.put(4, 5);
		matching1.put(5, 6);
		DualHashBidiMap<Integer, Integer> bidiMatching1 =
										new DualHashBidiMap<Integer, Integer>(matching1);
		
		HashSet<Integer> v1 = AssignmentMaker.findUnmatchedAwards(matching1);
		HashSet<Integer> expected1 = (HashSet<Integer>) v1.clone();
		
		AssignmentMaker.addAlternating(7, v1, bidiMatching1, adjMatrix1);
		expected1.add(13);
		expected1.add(5);
		expected1.add(11);
		expected1.add(2);
		expected1.add(9);
		expected1.add(1);
		
		assertTrue(expected1.equals(v1));
	}
	
	@Test
	public void testFindMatrixMin() {		
		AssignmentMaker.numNoms = 3;
		
		int[][] matrix1 = {{1, 0, 2},
						   {0, 0, 1},
						   {2, 2, 1}};
		
		assertEquals(1, AssignmentMaker.findMatrixMin(matrix1));
		

		int[][] matrix2 = {{3, 6, 2},
						   {5, 5, 3},
						   {2, 2, 7}};
		
		assertEquals(2, AssignmentMaker.findMatrixMin(matrix2));
	}
}
