package ro.ase.cts;

import java.util.ArrayList;

public class Student {
	private String name;

	private ArrayList<Integer> grades;

	public static final String[] FORBBIDEN_CHARS = { "@", "$", "!", "?", "#" };

	public static final int MAX_NAME = 100;

	public static final int MIN_NAME = 5;

	public Student(String name) {
		this.name = name;
		grades = new ArrayList<>();
	}

	public Student(String name, ArrayList<Integer> grades) {
		this.name = name;
		this.grades = new ArrayList<>();
		for (Integer grade : grades) {
			this.grades.add(grade);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidNameException {
		if (name.length() < MIN_NAME) {
			throw new InvalidNameException("Name lenght must be grater or equal then " + MIN_NAME);
		}
		if (name.length() > MAX_NAME) {
			throw new InvalidNameException("Name lenght must be less or equal then " + MAX_NAME);
		}
		for (String character : FORBBIDEN_CHARS) {
			if (name.contains(character)) {
				throw new InvalidNameException("Name contains an invalid character");
			}
		}
		this.name = name;
	}

	public ArrayList<Integer> getGrades() {
		return grades;
	}

	public void addGrade(int grade) throws WrongGradeException {
		if (grade > 10 || grade < 1) {
			throw new WrongGradeException("The grade must be between 1 and 10.");
		}
		grades.add(grade);
	}

	public int getGrade(int i) {
		return grades.get(i);
	}

	public int noOfPassedExams() {
		int noOfPassedExams = 0;

		for (Integer grade : grades) {
			if (grade >= 5) {
				noOfPassedExams++;
			}
		}

		return noOfPassedExams;
	}

	public float average() {
		int noOfPassedExams = this.noOfPassedExams();
		if (noOfPassedExams == 0) {
			return 0;
		}

		float average = 0;

		for (Integer grade : grades) {
			if (grade >= 5) {
				average += grade;
			}
		}

		return average / noOfPassedExams;
	}

	@Override
	public String toString() {
		String output = this.name + " grades: ";
		for (Integer grade : grades)
			output += grade + " ";
		return output;
	}
}
