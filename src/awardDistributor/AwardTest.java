package awardDistributor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AwardTest {

	@Test
	public void testAward() {
		Award a1 = new Award("Noble Prize");
		Award a2 = new Award("Turing Award");
		Award a3 = new Award("Physics - Maverick");
		Award a4 = new Award("Physics - Climber");
		
		assertEquals(a1.getIndex(), 0);
		assertEquals(a2.getIndex(), 1);
		assertEquals(a3.getIndex(), 2);
		assertEquals(a4.getIndex(), 3);
	}

	@Test
	public void testEqualsObject() {
		Award a1 = new Award("Physics Award");
		Award a2 = new Award("Physics Award");
		
		assertEquals(a1, a1);
		assertNotEquals(a1, a2);
	}
	
	@Test
	public void testGetRating() {
		Award a1 = new Award("Physics Award");
		String[] noms = {"Juan", "Arthur", "Jannet"};
		
		a1.setNoms(noms);
		
		assertEquals(a1.getRanking("Juan"), 1);
		assertEquals(a1.getRanking("Arthur"), 2);
		assertEquals(a1.getRanking("Jannet"), 3);
		
		try {
			a1.getRanking("Gina");
			fail();
		} catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void testHasNom() {
		Award a1 = new Award("Physics Award");
		String[] noms1 = {"Arturo", "Mauricio", "Monica"};
		a1.setNoms(noms1);
		
		assertTrue(a1.hasNom("Arturo"));
		assertTrue(a1.hasNom("Mauricio"));
		assertTrue(a1.hasNom("Monica"));
		assertFalse(a1.hasNom("Jannet"));
	}
	
	@Test
	public void testSetNoms() {
		Award a1 = new Award("Physics Award");
		String[] noms1 = {"Arturo", "Mauricio", "Arturo"};
		
		try {
			a1.setNoms(noms1);
			fail();
		} catch (IllegalArgumentException e) {}
	}

}
