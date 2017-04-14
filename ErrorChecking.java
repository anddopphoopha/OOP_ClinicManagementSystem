package clinic;

public class ErrorChecking {
	public boolean checkName(String Line) {
		if (Line.matches("[a-zA-Z]{3,30}")) { // space is not allowed, minimum 3
			return false;
		} else {
			System.out.println("Invalid Input,only letters are allowed,Minimum 3, maximum 30");
			return true;
		}
	}
	public boolean checkNumber(String Line) { // maximum 4 digits
		if (Line.matches("^[0-9]{1,4}$")) {
			return false;
		} else {
			System.out.println("Invalid Input, Maximum 4 digits\nThis is not a number");
			return true;
		}
	}
	public boolean checkUsernameAndPassword(String Line) {
		if (Line.matches("^[a-zA-Z0-9._-]{3,15}$")) {
			return false;
		} else {
			System.out.println("Invalid Input,Username can only contains letters and numbers, Minimum 3, Maximum 15");
			return true;
		}
	}

}
