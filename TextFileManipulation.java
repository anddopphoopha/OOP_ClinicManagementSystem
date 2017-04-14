package clinic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface TextFileManipulation {
	public default void writeLines(String path, String Line) {
		try {
			File txt = new File(path);
			byte[] buffer = Line.getBytes();
			FileOutputStream outputStream = new FileOutputStream(txt, true);
			outputStream.write(buffer);
			outputStream.write(13);
			outputStream.write(10);
			outputStream.close();
		} catch (IOException ex) {
			System.out.println("Error writing file to " + path);
		}
	}

	public default void readLines(Object instance, String path, Method method, String Keyword1, String Keyword2,
			String Keyword3, String Keyword4, String Keyword5, String Keyword6)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			File txt = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(txt));
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				method.invoke(instance, readLine, Keyword1, Keyword2, Keyword3, Keyword4, Keyword5, Keyword6);
			}
			reader.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public default Method getMethodObject(String className, String methodname)
			throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		Class<?> ClassInstance = Class.forName(className);
		return ClassInstance.getMethod(methodname, String.class, String.class, String.class, String.class, String.class,
				String.class, String.class);
	}

	public default String userPath() {
		return "D:/OOP_Project/userinfo.txt";
	}

	public default String appointmentPath() {
		return "D:/OOP_Project/Appointment.txt";
	}

	public default String tempFilePath() {
		return "D:/OOP_Project/temp.txt";
	}

	public default String messageFilePath() {
		return "D:/OOP_Project/Message.txt";
	}

	public default String messageTempFilePath() {
		return "D:/OOP_Project/Message_temp.txt";
	}
}
