package fr.miage.restfull.exception;

public class UserPasswordIncorrectException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserPasswordIncorrectException(){
		super("Password incorrect");
	}
}
