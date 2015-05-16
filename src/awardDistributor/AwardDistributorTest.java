package awardDistributor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class AwardDistributorTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindOptimalDistribution() {
		Award.resetIndex();
		
		AwardDistributor ad = new AwardDistributor(2);
		
		// Test #1: Straightforward
		Award phys = new Award("Physics");
		Award math = new Award("Math");
		
		ArrayList<String> noms = new ArrayList<String>();
		
		String s1 = "s1";
		noms.add(s1);
		String s2 = "s2";
		noms.add(s2);
		String s3 = "s3";
		noms.add(s3);
		String s4 = "s4";
		noms.add(s4);
		
		String[] physNoms1 = {s1, s2};
		phys.setNoms(physNoms1);
		String[] mathNoms1 = {s3, s4};
		math.setNoms(mathNoms1);
		
		Award[] awards1 = {phys, math};
		
		HashMap<Award, String> expected1 = new HashMap<Award, String>();
		expected1.put(phys, s1);
		expected1.put(math, s3);
		
		assertEquals(expected1, ad.findOptimalDistribution(awards1, noms));
		
		// Test #2: Overlapping rankings
		String[] physNoms2 = {s1, s2};
		phys.setNoms(physNoms2);
		String[] mathNoms2 = {s1, s2};
		math.setNoms(mathNoms2);
		
		Award[] awards2 = awards1;
		
		HashMap<Award, String> expected2 = new HashMap<Award, String>();
		expected2.put(math, s1);
		expected2.put(phys, s2);
		
		assertEquals(expected2, ad.findOptimalDistribution(awards2, noms));
		
		// Test #3: Too many overlapping rankings
		Award history = new Award("History");
		String[] histNoms3 = {s1, s2};
		history.setNoms(histNoms3);
		
		Award[] awards3 = {phys, math, history};
		
		try{
			ad.findOptimalDistribution(awards3, noms);
			fail();
		} catch (IllegalArgumentException e) {}
		
		// Test #4: More teachers than noms
		noms.remove(3);
		noms.remove(2);
		
		Award[] awards4 = awards3;
		
		try {
			ad.findOptimalDistribution(awards4, noms);
			fail();
		} catch (IllegalArgumentException e) {}
		
		// Test #5: More complicated pairings
		AwardDistributor ad2 = new AwardDistributor(3);
		
		String[] physNoms5 = {s1, s2, s3};
		phys.setNoms(physNoms5);
		
		String[] mathNoms5 = {s2, s4, s1};
		math.setNoms(mathNoms5);
		
		String[] histNoms5 = {s1, s2, s3};
		history.setNoms(histNoms5);
		
		noms.add(s3);
		noms.add(s4);
		
		Award[] awards5 = awards4;
		
		HashMap<Award, String> expected5 = new HashMap<Award, String>();
		expected5.put(math, s4);
		expected5.put(history, s1);
		expected5.put(phys, s2);
		
		assertEquals(expected5, ad2.findOptimalDistribution(awards5, noms));
	}

	@Test
	public void testGenerateRankingMatrix() {
		Award.resetIndex();
		
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
		
		assertArrayEquals(expected2, ad2.generateRankingMatrix(awards, noms));
	}

	@Test
	public void testParsePairings() {
		Award.resetIndex();
		
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
		
		HashMap<Integer, Integer> assignments1 = new HashMap<Integer, Integer>();
		assignments1.put(0, 0);
		assignments1.put(1, 2);
		assignments1.put(2, 3);
		assignments1.put(3, 4);
		assignments1.put(4, 1);
		
		HashMap<Award, String> expected = new HashMap<Award, String>();
		expected.put(a1, s1);
		expected.put(a2, s3);
		expected.put(a3, s4);
		
		assertTrue(expected.equals(ad1.parsePairings(assignments1, awards, noms)));
		
		HashMap<Integer, Integer> assignments2 = new HashMap<Integer, Integer>();
		assignments2.put(0, 2);
		assignments2.put(1, 0);
		assignments2.put(2, 3);
		assignments2.put(3, 4);
		assignments2.put(4, 1);
		
		try {
			ad1.parsePairings(assignments2, awards, noms);
			fail();
		} catch (IllegalArgumentException e) {}
	}
}
