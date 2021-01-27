package uk.cnv.client;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class LogManager {

	private static final String logfile;
	static {
		logfile = UkConvertProperty.getProperty(UkConvertProperty.LOG_FILE_PATH);
	}

	public static void out(String str) {
		System.out.println(str);
		outputLog(str);
	}

	public static void err(String str) {
		System.err.println(str);
		outputLog(str);
	}

	public static void err(Exception e) {
		System.err.println(e.getMessage());
		outputLog(e.getMessage());
		Arrays.stream(e.getStackTrace())
			.forEach(st -> outputLog(st.toString()));
	}

	private static void outputLog(String str) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logfile, true),"UTF-8"));

			bw.write(str);
			bw.newLine();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				if(bw != null) {
					bw.flush();
					bw.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
