package ro.ase.cts;

public class WrongGradeException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongGradeException(String message) {
		super(message);
	}
}
