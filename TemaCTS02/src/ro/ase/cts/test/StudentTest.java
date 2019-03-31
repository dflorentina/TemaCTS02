package ro.ase.cts.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.ase.cts.InvalidNameException;
import ro.ase.cts.Student;
import ro.ase.cts.WrongGradeException;

public class StudentTest {
	Student student;

	@Before
	public void setUp() throws Exception {
		student = new Student("Flori");
	}

	@After
	public void tearDown() throws Exception {
		student = null;
	}

	@Test
	public void testSetNameConformanceNormalValues() throws InvalidNameException {
		student.setName("Foori");
		assertEquals("Foori", student.getName());
	}

	@Test(expected = InvalidNameException.class)
	public void testSetNameConformanceBadValues() throws InvalidNameException {
		student.setName("Fl@ri");
	}

	@Test(expected = InvalidNameException.class)
	public void testSetNameRangeAboveLimit() throws InvalidNameException {
		String name = " ";
		for (int i = 0; i < 101; i++) {
			name += "a";
		}
		student.setName(name);
	}

	@Test(expected = InvalidNameException.class)
	public void testSetNameRangeBelowLimit() throws InvalidNameException {
		student.setName("A");
	}

	@Test
	public void testAddGradesConformanceNormalValues() throws WrongGradeException {
		student.addGrade(7);
		assertEquals(7, student.getGrade(0));
	}

	@Test(expected = WrongGradeException.class)
	public void testAddGradeRangeAboveLimit() throws WrongGradeException {
		student.addGrade(11);
	}

	@Test(expected = WrongGradeException.class)
	public void testAddGradeRangeBelowLimit() throws WrongGradeException {
		student.addGrade(-2);
	}

	@Test
	public void testAddGradeLowerLimit() throws WrongGradeException {
		student.addGrade(1);
		assertEquals(1, student.getGrade(0));
	}

	@Test
	public void testAddGradeUpperLimit() throws WrongGradeException {
		student.addGrade(10);
		assertEquals(10, student.getGrade(0));
	}

	@Test
	public void testAddGrades() throws WrongGradeException {
		for (int i = 1; i <= 10; i++) {
			student.addGrade(i);
			assertEquals(i, student.getGrade(i - 1));
		}
	}

	@Test
	public void testAverageCardinalityZero() throws WrongGradeException {
		student.addGrade(1);
		assertEquals(0f, student.average(), 0.1f);
	}

	@Test
	public void testAverageCardinalityOne() throws WrongGradeException {
		student.addGrade(5);
		assertEquals(5f, student.average(), 0.1f);
	}

	@Test
	public void testAverageCardinalityMany() throws WrongGradeException {
		student.addGrade(3);
		student.addGrade(6);
		student.addGrade(9);
		assertEquals(7.5f, student.average(), 0.1f);
	}

	@Test
	public void testAverageTime() throws WrongGradeException {
		Random rand = new Random();
		for (int i = 0; i < 100000; i++) {
			student.addGrade(rand.nextInt(9) + 1);
		}

		long start = System.currentTimeMillis();
		student.average();
		long finish = System.currentTimeMillis();

		if ((finish - start) < 1000) {
			assertTrue(true);
		} else {
			fail("Average computation takes too long");
		}
	}

	@Test
	public void testAverageOrdering() throws WrongGradeException {
		student.addGrade(9);
		student.addGrade(6);
		student.addGrade(3);
		Collections.sort(student.getGrades(), (g1, g2) -> g1 - g2);
		assertEquals(3, student.getGrade(0));
	}

	@Test
	public void testAddGradeExternalReference() throws WrongGradeException {
		student.addGrade(9);
		student.addGrade(9);
		student.addGrade(10);

		int initialGrade = student.getGrade(0);
		student.getGrades().add(0, 5);

		int actualGrade = student.getGrade(0);
		assertNotEquals(initialGrade, actualGrade);
	}

	@Test
	public void testAverageCrossCheck() throws WrongGradeException {
		student.addGrade(7);
		student.addGrade(8);
		student.addGrade(9);

		float expected = student.average();
		float calculated = (student.getGrade(0) + student.getGrade(1) + student.getGrade(2)) / 3;

		assertEquals(expected, calculated, 0.0f);
	}
	
	@Test
	public void testAddGradeInverse() throws WrongGradeException {
		student.addGrade(6);
		
		assertEquals(6, student.getGrade(0));
	}
}
