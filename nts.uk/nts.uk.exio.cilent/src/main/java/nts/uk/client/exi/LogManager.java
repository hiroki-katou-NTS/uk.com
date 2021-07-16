package nts.uk.client.exi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Arrays;

public class LogManager {

	private static final String logfile;

	static {
		logfile = ExiClientProperty.getProperty(ExiClientProperty.LOG_FILE_PATH);
	}

	public static void init() {
		File file = new File(logfile);
		if(file.exists()) return;
		
		try {
			Files.createFile(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void out(String str, Object ... args) {
		out(String.format(str, args));
	}

	public static void out(String str) {
		System.out.println(str);
		outputLog(str);
	}

	public static void err(String str, Object ... args) {
		err(String.format(str, args));
	}

	public static void err(String str) {
		System.err.println(str);
		outputLog(str);
	}

	public static void err(Exception e) {
		System.err.println(e.toString());
		outputLog(e.toString());

		if (e.getStackTrace() == null) return;

		Arrays.stream(e.getStackTrace())
			.forEach(st -> {
				System.err.println(st.toString());
				outputLog(st.toString());
			});
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
