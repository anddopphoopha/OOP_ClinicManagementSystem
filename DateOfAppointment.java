package clinic;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DateOfAppointment {
	private Scanner input = new Scanner(System.in);
	private Calendar calendar = Calendar.getInstance();
	protected int currentYear = calendar.get(Calendar.YEAR);
	protected int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
	protected int currentMonth = calendar.get(Calendar.MONTH) + 1;
	private int year;
	private int day;
	private int month;
	private String time;
	private ErrorChecking error = new ErrorChecking();
	public void setYear() {
		String temp = "";
		boolean checker = true;
		while (checker) {
			do {
				System.out.println("Please choose year: \n1) " + currentYear + "\n2) " + (currentYear + 1));
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			switch (Integer.parseInt(temp)) {
			case 1:
				year = currentYear;
				checker = false;
				break;
			case 2:
				year = currentYear + 1;
				checker = false;
				break;
			default:
				System.out.println("Input out of bounds");
				break;
			}
		}
	}

	public void setMonth() {
		String temp = "";
		boolean checker = true;
		while (checker) {
			do {
				displayMonth();
				System.out.println("Please input month: \nMin: 1 Max: 12");
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			month = Integer.parseInt(temp);
			if (month > 0 && month <= 12) {
				checker = false;
			} else {
				System.out.println("Input out of bounds");
			}
		}
	}

	public void setDay() {
		String temp = "";
		boolean checker = true;
		while (checker) {
			do {
				System.out.println("Please input day: \nMin: 1 Max: 30");
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			day = Integer.parseInt(temp);
			if (day > 0 && day <= 30) {
				checker = false;
			} else
				System.out.println("Input out of bounds");
		}
	}

	public void displayTime() {
		System.out.println("1) " + "09.00-10.00\n2) " + "10.00-11.00\n3) " + "11.00-12.00\n4) " + "12.00-13.00\n5) "
				+ "13.00-14.00\n6) " + "14.00-15.00\n7) " + "15.00-16.00\n8) " + "16.00-17.00");
	}

	public void displayMonth() {
		System.out.println("1) " + "January	2) " + "February\n3) " + "March	4) " + "April\n5) " + "May		6) "
				+ "June\n7) " + "July		8) " + "August\n9) " + "September	10) " + "October\n11) "
				+ "November	12) " + "December");
	}

	public void setTime() {
		String temp = "";
		boolean checker = true;
		while (checker) {
			do {
				displayTime();
				System.out.println("Please choose the time: ");
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			int choice = Integer.parseInt(temp);
			switch (choice) {
			case 1:
				time = "09.00-10.00";
				checker = false;
				break;
			case 2:
				time = "10.00-11.00";
				checker = false;
				break;
			case 3:
				time = "11.00-12.00";
				checker = false;
				break;
			case 4:
				time = "12.00-13.00";
				checker = false;
				break;
			case 5:
				time = "13.00-14.00";
				checker = false;
				break;
			case 6:
				time = "14.00-15.00";
				checker = false;
				break;
			case 7:
				time = "15.00-16.00";
				checker = false;
				break;
			case 8:
				time = "16.00-17.00";
				checker = false;
				break;
			default:
				System.out.println("This is an invalid input");
				break;
			}
		}
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public String getTime() {
		return time;
	}

	public double getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
		double time = Double.parseDouble(sdf.format(calendar.getTime()));
		return time;
	}

	public String getInputtedDate() {
		return year + "-" + month + "-" + day;
	}

	public String getCurrentDate() {
		return currentYear + "-" + currentMonth + "-" + currentDay;
	}

	public boolean isValidDate(String date) throws ParseException {
		String formatString;
		SimpleDateFormat format = null;
		formatString = "yyyy-MM-dd";
		format = new SimpleDateFormat(formatString);
		Date inputtedDate = format.parse(date);
		Date currentDate = format.parse(getCurrentDate());
		if (inputtedDate.before(currentDate)) {
			System.out.println("This date has already passed");
			return false;
		} else
			return true;
	}

	public boolean isValidDateTime() throws ParseException {
		String formatString;
		SimpleDateFormat format = null;
		formatString = "yyyy-MM-dd HH.mm";
		format = new SimpleDateFormat(formatString);
		Date inputtedDate = format.parse(getInputtedDate() + " " + getTime());
		Date currentDate = format.parse(getCurrentDate() + " " + getCurrentTime());
		if (inputtedDate.before(currentDate)) {
			System.out.println("This date has already passed");
			return false;
		} else
			return true;
	}

	public String setDate() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
		do {
			setYear();
			setMonth();
			setDay();
		} while (!isValidDate(getInputtedDate()));
		return getInputtedDate();

	}

}
