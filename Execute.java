package clinic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

public class Execute {

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException, IOException, ParseException {
		ClinicSystem clinic = new ClinicSystem();
		DateOfAppointment checkDate = new DateOfAppointment();
		DoctorAccount doctor = new DoctorAccount();
		PatientAccount patient = new PatientAccount();
		ReceptionistAccount receptionist = new ReceptionistAccount();
		ErrorChecking error = new ErrorChecking();
		Scanner input = new Scanner(System.in);
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
					} else if (choice == 2) {
						receptionist.displayPatientList();
						do {
							do {
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							select = Integer.parseInt(temp) - 1;
						} while (checkSelect(receptionist.patientlist, select));
						doctor.createAppointment(doctorFullname, receptionist.patientlist.get(select));
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
						}
					} else if (choice == 0)
						System.exit(0);
					else {
						System.out.println("Invalid Input");
					}
					doctor.appointmentList.clear();
					receptionist.patientlist.clear();
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
						} else {
							System.out.println("You do not have an appointment to change");
						}
					} else if (choice == 0) {
						System.exit(0);
					} else {
						System.out.println("Invalid input");
					}
					patient.appointmentList.clear();
				}

			} else if (account != null && account[4].equals("R")) {
				String receptionistFullname = account[0] + " " + account[1];
				System.out.println("Welcome, Receptionist " + receptionistFullname);
				boolean innerCheck = true;
				while (innerCheck) {
					String firstname, lastname, newUsername, newPassword;
					clinic.sortAllAppointment();
					do {
						System.out.println(
								"Please choose what would you like to do:\n[1] Register new doctor\n[2] Register new patient\n[3] View all of the appointment within duration\n[4] View doctor appointment at specific time\n[5] Change appointment\n[0] Logout");
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
							date1 = checkDate.setDate();
						} while (!checkDate.isValidDate(date1));
						do {
							System.out.println("Please provide second the date:");
							date2 = checkDate.setDate();
						} while (!checkDate.isValidDate(date2));
						receptionist.viewAppointmentByDuration(date1, date2);
					} else if (choice == 4) {
						do {
							receptionist.displayDoctorList();
							do {
								temp = input.nextLine();
							} while (error.checkNumber(temp));
							select = Integer.parseInt(temp) - 1;
						} while (checkSelect(receptionist.doctorlist, select));
						String date;
						do {
							System.out.println("Please provide the date:");
							date = checkDate.setDate();
							if (!checkDate.isValidDate(date)) {
								System.out.println("This is a invalid date");
							}
						} while (!checkDate.isValidDate(date));
						receptionist.viewAppointmentOfSpecificDoctorAndDate(receptionist.doctorlist.get(select), date);
					} else if (choice == 5) {
						receptionist.displayAppointment();
						if (!receptionist.appointmentList.isEmpty()) {
							do {
								do {
									temp = input.nextLine();
								} while (error.checkNumber(temp));
								select = Integer.parseInt(temp) - 1;
							} while (checkSelect(receptionist.appointmentList, select));
							String[] token = receptionist.appointmentList.get(select).split(",");
							receptionist.changeAppointment(token[0], token[1], token[2], token[3]);
						}
					} else if (choice == 0) {
						System.exit(0);
					} else {
						System.out.println("Invalid input");
					}
					receptionist.doctorlist.clear();
					receptionist.appointmentList.clear();
				}
			} else {
				System.out.println("Incorrect Input");
			}

		}
		input.close();
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
