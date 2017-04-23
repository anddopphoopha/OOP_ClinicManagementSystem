package clinic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public interface IClinicStaff extends TextFileManipulation {
	ClinicSystem clinic = new ClinicSystem();

	public default void changeAppointment(String doctor, String patient, String date, String time)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, IOException {
		File newFile = new File(tempFilePath());
		newFile.createNewFile();
		readLines(this, appointmentPath(), getMethodObject("clinic.IClinicStaff", "writeToAppointment"), doctor,
				patient, date, time, null, null);
		File oldFile = new File(appointmentPath());
		oldFile.delete();
		newFile.renameTo(oldFile);
	}

	public default void writeToAppointment(String readLine, String doctor, String patient, String date, String time,
			String keyword5, String keyword6)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException, ParseException {
		String[] token = readLine.split(",");
		if (token[0].equals(doctor) && token[1].equals(patient) && token[2].equals(date) && token[3].equals(time)) {
			String temp = doctor + "," + patient + "," + clinic.setAppointmentDate(doctor, patient);
			writeLines(tempFilePath(), temp);
		} else {
			writeLines(tempFilePath(), readLine);
		}
	}
}
