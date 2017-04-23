package clinic;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public interface ICreatable {
	public void createAppointment(String doctor, String patient)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException, ParseException;
}
