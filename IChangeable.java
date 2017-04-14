package clinic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface IChangeable {
	public void changeAppointment(String doctor, String patient, String date, String time)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, IOException;

	public void writeToAppointment(String readLine, String doctor, String patient, String date, String time,
			String keyword5, String keyword6) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException;
}
