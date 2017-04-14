package clinic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Execute {
	private static Scanner input;

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, IOException {
		ClinicSystem clinic = new ClinicSystem();
		Doctor doctor = new Doctor();
		Patient patient = new Patient();
		Receptionist receptionist = new Receptionist();
		ErrorChecking error = new ErrorChecking();
		input = new Scanner(System.in);
		boolean outerCheck = true;
		while (outerCheck) {
			clinic.sortAllAppointment();
			String username, password, temp;
			int choice, select;
			do {
				System.out.print("Input Username: ");
				username = input.nextLine();
			} while (error.checkUsernameAndPassword(username));
			do {
				System.out.print("Input Password: ");
				password = input.nextLine();
			} while (error.checkUsernameAndPassword(password));
			String[] account = clinic.logIn(username, password);
			if (account != null && account[4].equals("D")) {
				String doctorFullname = account[0] + " " + account[1];
				System.out.println("Welcome, Doctor " + doctorFullname);
				boolean innerCheck = true;
				while (innerCheck) {
					clinic.sortAllAppointment();
					doctor.getRequestList(doctorFullname);
					doctor.approveChange();
					do {
						System.out.println(
								"Please choose what would you like to do:\n[1] Display your upcoming appointments\n[2] Create appointment\n[3] Change Appointment\n[0] Logout");
						temp = input.nextLine();
					} while (error.checkNumber(temp));
					choice = Integer.parseInt(temp);
					if (choice == 1) {
						doctor.viewAppointment(doctorFullname);
						doctor.appointmentList.clear();
					} else if (choice == 2) {
						do {
							receptionist.displayPatientList();
							do {
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							select = Integer.parseInt(temp) - 1;
						} while (checkSelect(receptionist.patientlist, select));
						doctor.createAppointment(doctorFullname, receptionist.patientlist.get(select));
						receptionist.patientlist.clear();
					} else if (choice == 3) {
						doctor.viewAppointment(doctorFullname);
						if (!doctor.appointmentList.isEmpty()) {
							do {
								do {
									System.out
											.println("Please choose from the list above which appointment to change: ");
									temp = input.nextLine();
								} while (error.checkNumber(temp));
								select = Integer.parseInt(temp) - 1;
							} while (checkSelect(doctor.appointmentList, select));
							String[] token = doctor.appointmentList.get(select).split(",");
							doctor.changeAppointment(token[0], token[1], token[2], token[3]);
							doctor.appointmentList.clear();
						} else
							System.out.println("You do not have any appointment to change");
					} else if (choice == 0)
						System.exit(0);
					else
						System.out.println("Invalid Input");
				}
			} else if (account != null && account[4].equals("P")) {
				String patientFullname = account[0] + " " + account[1];
				System.out.println("Welcome, Patient " + patientFullname);
				boolean innerCheck = true;
				while (innerCheck) {
					clinic.sortAllAppointment();
					do {
						System.out.println(
								"Please choose what would you like to do:\n[1] View next appointment\n[2] Request to change appointment\n[0] Logout");
						temp = input.nextLine();
					} while (error.checkNumber(temp));
					choice = Integer.parseInt(temp);
					if (choice == 1) {
						patient.viewAppointment(patientFullname);
					} else if (choice == 2) {
						patient.viewAppointment(patientFullname);
						if (!patient.appointmentList.isEmpty()) {
							String[] token = patient.appointmentList.get(0).split(",");
							patient.sendRequestToMessageFile(token[0], token[1], token[2], token[3]);
							patient.appointmentList.clear();
						} else {
							System.out.println("You do not have an appointment to change");
						}
					} else if (choice == 0) {
						System.exit(0);
					} else {
						System.out.println("Invalid input");
					}
				}
			} else if (account != null && account[4].equals("R")) {
				String receptionistFullname = account[0] + " " + account[1];
				System.out.println("Welcome, Receptionist " + receptionistFullname);
				boolean innerCheck = true;
				while (innerCheck) {
					String firstname, lastname, newUsername, newPassword;
					int year, month, day;
					clinic.sortAllAppointment();
					do {
						System.out.println(
								"Please choose what would you like to do:\n[1] Register new doctor\n[2] Register new patient\n[3] View all of the appointment\n[4] View doctor appointment at specific time\n[5] Change appointment\n[0] Logout");
						temp = input.nextLine();
					} while (error.checkNumber(temp));
					choice = Integer.parseInt(temp);
					if (choice == 1) {
						do {
							System.out.print("Please input firstname: ");
							firstname = input.nextLine();
						} while (error.checkName(firstname));
						do {
							System.out.print("Please input lastname: ");
							lastname = input.nextLine();
						} while (error.checkName(lastname));
						do {
							System.out.print("Please input username: ");
							newUsername = input.nextLine();
						} while (error.checkUsernameAndPassword(newUsername));
						do {
							System.out.print("Please input password: ");
							newPassword = input.nextLine();
						} while (error.checkUsernameAndPassword(newPassword));
						receptionist.createRole(firstname, lastname, newUsername, newPassword, 'D');
					} else if (choice == 2) {
						do {
							System.out.print("Please input firstname: ");
							firstname = input.nextLine();
						} while (error.checkName(firstname));
						do {
							System.out.print("Please input lastname: ");
							lastname = input.nextLine();
						} while (error.checkName(lastname));
						do {
							System.out.print("Please input username: ");
							newUsername = input.nextLine();
						} while (error.checkUsernameAndPassword(newUsername));
						do {
							System.out.print("Please input password: ");
							newPassword = input.nextLine();
						} while (error.checkUsernameAndPassword(newPassword));
						receptionist.createRole(firstname, lastname, newUsername, newPassword, 'P');
					} else if (choice == 3) {
						String date1, date2;
						do {
							System.out.println("Please provide the date:");
							year = chooseYear(error);
							do {
								System.out.print("Input month: ");
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							month = Integer.parseInt(temp);
							do {
								System.out.print("Input day: ");
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							day = Integer.parseInt(temp);
							date1 = year + "-" + month + "-" + day;
							if (!isValidDate(date1)) {
								System.out.println("This is a invalid date");
							}
						} while (!isValidDate(date1));
						do {
							System.out.println("Please provide second the date:");
							year = chooseYear(error);
							do {
								System.out.print("Input month: ");
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							month = Integer.parseInt(temp);
							do {
								System.out.print("Input day: ");
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							day = Integer.parseInt(temp);
							date2 = year + "-" + month + "-" + day;
							if (!isValidDate(date2)) {
								System.out.println("This is a invalid date");
							}
						} while (!isValidDate(date2));
						receptionist.viewAppointmentByDuration(date1, date2);
					} else if (choice == 4) {
						receptionist.displayDoctorList();
						do {
							do {
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							select = Integer.parseInt(temp) - 1;
						} while (checkSelect(receptionist.doctorlist, select));
						String date;
						do {
							year = chooseYear(error);
							do {
								System.out.print("Input month: ");
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							month = Integer.parseInt(temp);
							do {
								System.out.print("Input day: ");
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							day = Integer.parseInt(temp);
							date = year + "-" + month + "-" + day;
							if (!isValidDate(date)) {
								System.out.println("This is a invalid date");
							}
						} while (!isValidDate(date));
						receptionist.viewAppointmentOfSpecificDoctorAndDate(receptionist.doctorlist.get(select), date);
						receptionist.doctorlist.clear();
					} else if (choice == 5) {
						receptionist.displayAppointment();
						do {
							do {
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							select = Integer.parseInt(temp) - 1;
						} while (checkSelect(receptionist.appointmentList, select));
						String[] token = receptionist.appointmentList.get(select).split(",");
						receptionist.changeAppointment(token[0], token[1], token[2], token[3]);
					} else if (choice == 0) {
						System.exit(0);
					} else {
						System.out.println("Invalid input");
					}
				}
			} else {
				System.out.println("Incorrect Input");
			}
		}
		input.close();
	}

	public static boolean isValidDate(String input) {

		String formatString = "yyyy-MM-dd";
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			format.setLenient(false);
			format.parse(input);
		} catch (ParseException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public static int chooseYear(ErrorChecking error) {
		input = new Scanner(System.in);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String temp;
		boolean checker = true;
		int year = 0;
		while (checker) {
			do {
				System.out.println("Choose year: \n1) " + currentYear + "\n2) " + (currentYear + 1));
				temp = input.nextLine();
			} while (error.checkNumber(temp));
			if (Integer.parseInt(temp) == 1) {
				year = currentYear;
				break;
			} else if (Integer.parseInt(temp) == 2) {
				year = currentYear + 1;
				break;
			} else {

			}
		}
		return year;
	}

	public static boolean checkSelect(ArrayList<String> appointmentList, int select) {
		if (select < appointmentList.size() && select >= 0) {
			return false;
		} else {
			System.out.println("This is out of bounds\nPlease input again");
			return true;
		}
	}
}
