package clinic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		boolean checker = true;
		while (checker) {
			String temp = "";
			do {
				System.out.println("Please choose year: \n1) " + currentYear + "\n2) " + (currentYear + 1));
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			switch (Integer.parseInt(temp)) {
			case 1:
				year = currentYear;
				break;
			case 2:
				year = currentYear + 1;
				break;
			default:
				System.out.println("This is an invalid input");
				break;
			}
			if (year >= currentYear && year <= (currentYear + 1)
					&& (year != currentYear || currentMonth != 12 || currentDay != 31 || getCurrentTime() < 16.00)) {
				break;
			}
		}
	}

	public void setMonth() {
		boolean checker = true;
		while (checker) {
			String temp = "";
			do {
				System.out.print("Please input month: ");
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			month = Integer.parseInt(temp);
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if (month >= 1 && month <= 12 && (month >= currentMonth || year > currentYear)
						&& (currentDay != 31 || currentMonth != month || getCurrentTime() < 16.00)) {
					checker = false;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if (month >= 1 && month <= 12 && (month >= currentMonth || year > currentYear)
						&& (currentDay != 30 || currentMonth != month || getCurrentTime() < 16.00)) {
					checker = false;
				}
				break;
			case 2:
				if (year % 4 == 0) {
					if (month >= 1 && month <= 12 && (month >= currentMonth || year > currentYear)
							&& (currentDay != 29 || currentMonth != month || getCurrentTime() < 16.00)) {
						checker = false;
					}
				} else {
					if (month >= 1 && month <= 12 && (month >= currentMonth || year > currentYear)
							&& (currentDay != 28 || currentMonth != month || getCurrentTime() < 16.00)) {
						checker = false;
					}
				}

			}
		}
	}

	public void setDay() {
		boolean checker = true;
		while (checker) {
			String temp = "";
			do {
				System.out.print("Please input day: ");
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			day = Integer.parseInt(temp);
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if (day >= 1 && day <= 31 && (day >= currentDay || month > currentMonth || year > currentYear)
						&& (getCurrentTime() < 16.00 || currentDay != day || month != currentMonth)) {
					checker = false;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if (day >= 1 && day <= 30 && (day >= currentDay || month > currentMonth || year > currentYear)
						&& (getCurrentTime() < 16.00 || currentDay != day || month != currentMonth)) {
					checker = false;
				}
				break;
			case 2:
				if (year % 4 == 0) {
					if (day >= 1 && day <= 29 && (day >= currentDay || month > currentMonth)
							&& getCurrentTime() < 16.00) {
						checker = false;
					}
				} else {
					if (day >= 1 && day <= 28 && (day >= currentDay || month > currentMonth)
							&& getCurrentTime() < 16.00) {
						checker = false;
					}
				}

			}
		}
	}

	public void displayTime() {
		System.out.println("1) " + "09.00-10.00");
		System.out.println("2) " + "10.00-11.00");
		System.out.println("3) " + "11.00-12.00");
		System.out.println("4) " + "12.00-13.00");
		System.out.println("5) " + "13.00-14.00");
		System.out.println("6) " + "14.00-15.00");
		System.out.println("7) " + "15.00-16.00");
		System.out.println("8) " + "16.00-17.00");
	}

	public void setTime() {
		boolean checker = true;
		while (checker) {
			displayTime();
			String temp = "";
			do {
				System.out.println("Please choose the time: ");
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			int choice = Integer.parseInt(temp);
			switch (choice) {
			case 1:
				if (getCurrentTime() <= 9.00 || day > currentDay || month > currentMonth || year > currentYear) {
					time = "09.00-10.00";
					checker = false;
				}
				break;
			case 2:
				if (getCurrentTime() <= 10.00 || day > currentDay || month > currentMonth || year > currentYear) {
					time = "10.00-11.00";
					checker = false;
				}
				break;
			case 3:
				if (getCurrentTime() <= 11.00 || day > currentDay || month > currentMonth || year > currentYear) {
					time = "11.00-12.00";
					checker = false;
				}
				break;
			case 4:
				if (getCurrentTime() <= 12.00 || day > currentDay || month > currentMonth || year > currentYear) {
					time = "12.00-13.00";
					checker = false;
				}
				break;
			case 5:
				if (getCurrentTime() <= 13.00 || day > currentDay || month > currentMonth || year > currentYear) {
					time = "13.00-14.00";
					checker = false;
				}
				break;
			case 6:
				if (getCurrentTime() <= 14.00 || day > currentDay || month > currentMonth || year > currentYear) {
					time = "14.00-15.00";
					checker = false;
				}
				break;
			case 7:
				if (getCurrentTime() <= 15.00 || day > currentDay || month > currentMonth || year > currentYear) {
					time = "15.00-16.00";
					checker = false;
				}
				break;
			case 8:
				if (getCurrentTime() <= 17.00 || day > currentDay || month > currentMonth || year > currentYear) {
					time = "16.00-17.00";
					checker = false;
				}
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
}
