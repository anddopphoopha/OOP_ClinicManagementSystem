package clinic;

import java.lang.reflect.InvocationTargetException;

public class Patient extends ClinicSystem implements IViewableRestricted, TextFileManipulation {
	public void viewAppointment(String patientname) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		readLines(this, appointmentPath(), getMethodObject("clinic.Patient", "readPatientName"), patientname, null,
				null, null, null, null);
		if (!appointmentList.isEmpty())
			System.out.println(appointmentList.get(0));
		else {
			System.out.println("You have no upcoming appointment");
		}
	}

	public void readPatientName(String readLine, String patientname, String keyword2, String keyword3, String keyword4,
			String keyword5, String keyword6) {
		String[] token = readLine.split(",");
		if (token[1].equals(patientname)) {
			appointmentList.add(readLine);
		}
	}

	public void sendRequestToMessageFile(String doctor, String patient, String date, String time)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		System.out.println("Please input date you would like to change to: ");
		readLines(this, appointmentPath(), getMethodObject("clinic.Patient", "readAppointment"), doctor, patient, date,
				time, null, null);
		System.out.println("Yeah finished");

	}

	public void readAppointment(String readLine, String doctor, String patient, String date, String time,
			String keyword5, String keyword6) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		String[] token = readLine.split(",");
		if (token[0].equals(doctor) && token[1].equals(patient) && token[2].equals(date) && token[3].equals(time)) {
			String Line = doctor + "," + patient + "," + date + "," + time + "," + inputAppointment(doctor, patient);
			writeLines(messageFilePath(), Line);
		}
	}
}
