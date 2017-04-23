package clinic;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceptionistAccount extends ClinicSystem implements IClinicStaff {
	protected ArrayList<String> patientlist = new ArrayList<String>();
	protected ArrayList<String> doctorlist = new ArrayList<String>();
	private ArrayList<String> userlist = new ArrayList<String>();
	private boolean check = true;

	public void createRole(String firstname, String lastname, String username, String password, char role)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		if (checkDuplicate(firstname, lastname, username)) {
			String user = firstname + "," + lastname + "," + username + "," + password + "," + role;
			writeLines(userPath(), user);
			System.out.println("Registration completed");
		}
	}

	public boolean checkDuplicate(String firstname, String lastname, String username)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		readLines(this, userPath(), getMethodObject("clinic.ReceptionistAccount", "readUserInfo"), firstname, lastname,
				username, null, null, null);
		boolean checker = true;
		for (int i = 0; i < userlist.size(); i++) {
			String[] token = userlist.get(i).split(",");
			if ((token[0].equals(firstname) && token[1].equals(lastname))) {
				System.out.println("This is name is already used, please try another one");
				if (token[2].equals(username)) {
					System.out.println("This is username is already used, please try another one");
				}
				checker = false;
				break;
			} else if (token[2].equals(username)) {
				System.out.println("This is username is already used, please try another one");
				checker = false;
				break;
			}
		}
		return checker;
	}

	public void readUserInfo(String readLine, String firstname, String lastname, String username, String keyword4,
			String keyword5, String keyword6) {
		userlist.add(readLine);
	}

	public void displayDoctorList() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		readLines(this, userPath(), getMethodObject("clinic.ReceptionistAccount", "readDoctorList"), null, null, null,
				null, null, null);
		System.out.println("Please choose from the following doctor list Below: ");
		loopList(doctorlist);
	}

	public void readDoctorList(String readLine, String keyword1, String keyword2, String keyword3, String keyword4,
			String keyword5, String keyword6) {
		String[] token = readLine.split(",");
		if (token[4].equals("D")) {
			doctorlist.add(token[0] + " " + token[1]);
		}
	}

	public void loopList(ArrayList<String> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			System.out.println(i + 1 + ") " + ArrayList.get(i));
		}
	}

	public void displayPatientList() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		readLines(this, userPath(), getMethodObject("clinic.ReceptionistAccount", "readPatientList"), null, null, null,
				null, null, null);
		System.out.println("Please choose from the following Patient list Below: ");
		loopList(patientlist);
	}

	public void readPatientList(String readLine, String keyword1, String keyword2, String keyword3, String keyword4,
			String keyword5, String keyword6) {
		String[] token = readLine.split(",");
		if (token[4].equals("P")) {
			patientlist.add(token[0] + " " + token[1]);
		}
	}

	public void viewAppointmentOfSpecificDoctorAndDate(String doctor, String date)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		check = false;
		readLines(this, appointmentPath(),
				getMethodObject("clinic.ReceptionistAccount", "readAppointmentOfSpecificDoctorAndDate"), doctor, date,
				null, null, null, null);
		if (!check) {
			System.out.println("The doctor has no appointment at this time");
		}
	}

	public void readAppointmentOfSpecificDoctorAndDate(String readLine, String doctor, String date, String keyword3,
			String keyword4, String keyword5, String keyword6) {
		String[] token = readLine.split(",");
		if (token[0].equals(doctor) && token[2].equals(date)) {
			System.out.println(readLine);
			check = true;
		}

	}

	public void viewAppointmentByDuration(String date1, String date2)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		readLines(this, appointmentPath(), getMethodObject("clinic.ReceptionistAccount", "readAppointmentByDuration"),
				date1, date2, null, null, null, null);
	}

	public void readAppointmentByDuration(String readLine, String date1, String date2, String keyword3, String keyword4,
			String keyword5, String keyword6) throws ParseException {
		String[] token = readLine.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date tempDate1 = sdf.parse(date1);
		Date tempDate2 = sdf.parse(date2);
		Date checkDate = sdf.parse(token[2]);
		if (tempDate1.before(tempDate2)) {
			if ((checkDate.after(tempDate1) || checkDate.equals(tempDate1))
					&& (checkDate.before(tempDate2) || checkDate.equals(tempDate2)))
				System.out.println(readLine);
		} else if (tempDate1.after(tempDate2)) {
			if ((checkDate.after(tempDate2) || checkDate.equals(tempDate2))
					&& (checkDate.before(tempDate1) || checkDate.equals(tempDate1)))
				System.out.println(readLine);
		} else
			System.out.println("There are no appointments within this time period");
	}

	public void displayAppointment() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		readLines(this, appointmentPath(), getMethodObject("clinic.ReceptionistAccount", "getAppointmentList"), null,
				null, null, null, null, null);
		if (!appointmentList.isEmpty()) {
			for (int i = 0; i < appointmentList.size(); i++) {
				System.out.println(i + 1 + ") " + appointmentList.get(i));
			}
			System.out.println("Please select the appointment you would like to change: ");
		} else {
			System.out.println("You have no appointment to change");
		}
	}

}
