package clinic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class DoctorAccount extends ClinicSystem implements ICreatable, IViewable, IClinicStaff {
	protected ArrayList<String> message = new ArrayList<String>();
	Scanner input = new Scanner(System.in);

	public void viewAppointment(String doctor) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		readLines(this, appointmentPath(), getMethodObject("clinic.DoctorAccount", "readOwnSchedule"), doctor, null,
				null, null, null, null);
		if (!appointmentList.isEmpty()) {
			for (int i = 0; i < appointmentList.size(); i++) {
				System.out.println(i + 1 + ")" + appointmentList.get(i));
			}
		} else {
			System.out.println("You have no upcoming appointments");
		}
	}

	public void readOwnSchedule(String readLine, String doctor, String keyword2, String keywrod3, String keyword4,
			String keyword5, String keyword6) {
		String[] token = readLine.split(",");
		if (token[0].equals(doctor)) {
			if (compareDates(token[2])) {
				appointmentList.add(readLine);
			}
		}
	}

	public void approveChange() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException, IOException {
		if (!message.isEmpty()) {
			File newAppointment = new File(tempFilePath());
			newAppointment.createNewFile();
			for (int i = 0; i < message.size(); i++) {
				readLines(this, appointmentPath(), getMethodObject("clinic.DoctorAccount", "getAppointmentList"), null,
						null, null, null, null, null);
				System.out.println(i + 1 + ") " + message.get(i));
				System.out.println("[1] Approve\n[2] Deny");
				int choice = input.nextInt();
				if (choice == 1) {
					String[] messageToken = message.get(i).split(",");
					for (int j = 0; j < appointmentList.size(); j++) {
						String[] token2 = appointmentList.get(j).split(",");
						if (messageToken[0].equals(token2[0]) && messageToken[1].equals(token2[1])
								&& messageToken[2].equals(token2[2]) && messageToken[3].equals(token2[3])) {
							String appointment = messageToken[0] + "," + messageToken[1] + "," + messageToken[4] + ","
									+ messageToken[5];
							writeLines(tempFilePath(), appointment);
						} else {
							writeLines(tempFilePath(), appointmentList.get(j));
						}
					}
					File oldAppointment = new File(appointmentPath());
					oldAppointment.delete();
					newAppointment.renameTo(oldAppointment);
				}
			}
			appointmentList.clear();
			message.clear();
		}

	}

	public void getRequestList(String doctor) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, IOException {
		File newMessage = new File(messageTempFilePath());
		newMessage.createNewFile();
		readLines(this, messageFilePath(), getMethodObject("clinic.DoctorAccount", "readRequestFromMessageFile"),
				doctor, null, null, null, null, null);
		File oldMessage = new File(messageFilePath());
		oldMessage.delete();
		newMessage.renameTo(oldMessage);
	}

	public void readRequestFromMessageFile(String readLine, String doctor, String patient, String date, String time,
			String Newdate, String NewTime) {
		String[] token = readLine.split(",");
		if (token[0].equals(doctor)) {
			message.add(readLine);
		} else if (readLine.equals("")) {
		} else {
			writeLines(messageTempFilePath(), readLine);
		}
	}

	public void createAppointment(String doctor, String patient)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException, ParseException {
		writeLines(appointmentPath(), doctor + "," + patient + "," + setAppointmentDate(doctor, patient));
	}
}
