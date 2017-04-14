package clinic;

import java.lang.reflect.InvocationTargetException;

public interface ICreatable {
	public void createAppointment(String doctor, String patient)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException;
}
