package clinic;

import java.lang.reflect.InvocationTargetException;

public interface IViewableRestricted {
	public void viewAppointment(String person) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException;
}
