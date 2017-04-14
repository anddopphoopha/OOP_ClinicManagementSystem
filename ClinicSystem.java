package clinic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClinicSystem implements TextFileManipulation {
	protected ArrayList<String> appointmentList = new ArrayList<String>();
	DateOfAppointment appointment = new DateOfAppointment();
	private String[] account;
	private boolean check = true;

	public String[] logIn(String username, String password) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		this.account = null;
		readLines(this, userPath(), getMethodObject("clinic.ClinicSystem", "authentication"), username, password, null,
				null, null, null);
		return account;
	}

	public void authentication(String readLine, String username, String password, String keyword3, String keyword4,
			String keyword5, String keyword6) throws ParseException {
		String[] token = readLine.split(",");
		if (username.equals(token[2]) && password.equals(token[3])) {
			this.account = token;
		}
	}

	public void checkAvailability(String doctor, String patient)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		readLines(this, appointmentPath(), getMethodObject("clinic.ClinicSystem", "checkAppointment"), doctor, patient,
				null, null, null, null);
	}

	public String inputAppointment(String doctor, String patient)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		do {
			appointment.setYear();
			appointment.setMonth();
			appointment.setDay();
			appointment.setTime();
			checkAvailability(doctor, patient);
		} while (!check);
		check = true;
		return appointment.getInputtedDate() + "," + appointment.getTime();

	}

	public void checkAppointment(String readLine, String doctor, String patient, String keyword3, String keyword4,
			String keyword5, String keyword6) {
		String[] token = readLine.split(",");
		if (token[0].equals(doctor) && token[2].equals(appointment.getInputtedDate())
				&& token[3].equals(appointment.getTime())) {
			System.out.println("The doctor unavailable at this time");
			this.check = false;
		} else if (token[1].equals(patient) && token[2].equals(appointment.getInputtedDate())
				&& token[3].equals(appointment.getTime())) {
			System.out.println("The patient unavailable at this time");
			this.check = false;
		}
	}

	public void getAppointment(String readLine, String keyword1, String keyword2, String keyword3, String keyword4,
			String keyword5, String keyword6) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm");
		String[] date = readLine.split(",");
		String tempDate = date[2] + " " + date[3];
		Date date1 = sdf.parse(tempDate);
		Date date2 = sdf.parse(appointment.getCurrentDate() + " " + appointment.getCurrentTime());
		if (date1.after(date2)) {
			appointmentList.add(readLine);
		}
	}

	public boolean compareDates(String date, String time) {
		String[] token = time.split("-");
		if (getDifference(date, token[0])) {
			return true;
		}
		return false;
	}

	public boolean getDifference(String date, String time) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		long diff = 0;
		long temp = 0;
		try {
			Date date1 = myFormat.parse(appointment.getCurrentDate());
			Date date2 = myFormat.parse(date);
			diff = date2.getTime() - date1.getTime();
			temp = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (temp <= 7 && temp >= 0 && (temp > 0 || Double.parseDouble(time) >= appointment.getCurrentTime())) {
			return true;
		}
		return false;
	}

	public void sortAllAppointment() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException, IOException {
		readLines(this, appointmentPath(), getMethodObject("clinic.ClinicSystem", "getAppointment"), null, null, null,
				null, null, null);
		File newFile = new File(tempFilePath());
		newFile.createNewFile();
		sortAppointments(appointmentList);
		for (int i = 0; i < appointmentList.size(); i++) {
			writeLines(tempFilePath(), appointmentList.get(i));
		}
		File oldFile = new File(appointmentPath());
		oldFile.delete();
		newFile.renameTo(oldFile);
		appointmentList.clear();

	}

	public void sortAppointments(ArrayList<String> sort) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm");
			for (int outer = 0; outer < sort.size(); outer++) {
				for (int inner = outer + 1; inner < sort.size(); inner++) {
					String[] token1 = sort.get(inner).split(",");
					String[] token2 = sort.get(outer).split(",");
					String[] timeSplit1 = token1[3].split("-");
					String[] timeSplit2 = token2[3].split("-");
					String temp = token1[2] + " " + timeSplit1[0];
					String temp2 = token2[2] + " " + timeSplit2[0];
					Date date1 = sdf.parse(temp);
					Date date2 = sdf.parse(temp2);
					if (date1.before(date2)) {
						String tempVar = sort.get(inner);
						sort.remove(inner);
						sort.add(outer, tempVar);
					}
				}
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
	}
}
