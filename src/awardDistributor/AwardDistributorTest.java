package awardDistributor;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class AwardDistributorTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOptimalDistribution() {
		//TODO Test findOptimalDistribution method
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateRankingMatrix() {
		AwardDistributor ad1 = new AwardDistributor(2);
		Award a1 = new Award("Physics Mav");
		Award a2 = new Award("Physics Climber");
		Award a3 = new Award("Algebra Mav");
		
		String s1 = "Juan";
		String s2 = "Marisol";
		String s3 = "Javier";
		String s4 = "Barbara";
		String s5 = "Maria";
		
		String[] a1Noms = {s1, s2};
		a1.setNoms(a1Noms);
		
		String[] a2Noms = {s3, s4};
		a2.setNoms(a2Noms);
		
		String[] a3Noms = {s4, s5};
		a3.setNoms(a3Noms);
		
		ArrayList<String> noms = new ArrayList<String>(5);
		noms.add(s1);
		noms.add(s2);
		noms.add(s3);
		noms.add(s4);
		noms.add(s5);
		Award[] awards = {a1, a2, a3};
		
		int[][] expected1 = {{1,   2,   105, 105, 105},
							 {105, 105, 1,   2,   105},
							 {105, 105, 105, 1,     2},
							 {105, 105, 105, 105, 105},
							 {105, 105, 105, 105, 105}};
		
		assertArrayEquals(expected1, ad1.generateRankingMatrix(awards, noms));
		
		
		AwardDistributor ad2 = new AwardDistributor(3);
		String[] allNoms = {s1, s2, s3};
		
		a1.setNoms(allNoms);
		a2.setNoms(allNoms);
		a3.setNoms(allNoms);
		
		noms.clear();
		noms.add(s1);
		noms.add(s2);
		noms.add(s3);
		
		int[][] expected2 = {{1, 2, 3},
							 {1, 2, 3},
							 {1, 2, 3}};
		
		System.out.println(noms.size());
		
		assertArrayEquals(expected2, ad2.generateRankingMatrix(awards, noms));
	}

	@Test
	public void testParsePairings() {
		//TODO Test parsePairings method
		fail("Not yet implemented");
	}
}
