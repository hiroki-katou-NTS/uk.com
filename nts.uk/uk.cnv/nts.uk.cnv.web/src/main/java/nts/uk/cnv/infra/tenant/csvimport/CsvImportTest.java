package nts.uk.cnv.infra.tenant.csvimport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.gul.file.FileUtil;

@AllArgsConstructor
public class CsvImportTest {
	private static final String DEFAULT_ENCODE = "Shift_JIS";
	private static final String COPY_SQL_FILE = "copy.sql";

	private static final String COPY_EXEC_COMMAND =
			  "@ECHO OFF\r\n"
			+ "SET PGPASSWORD=%s\r\n"
			+ "psql -h %s -d %s -U %s -f " + COPY_SQL_FILE + "\r\n"
			+ "exit";

	String contractCode;
	Path zipFile;
	List<String> fileList;

	public void importCsv() {
    	val factory = new ApplicationTemporaryFileFactory();
    	val container = factory.createContainer();

    	container.unzipHere(zipFile);

    	val copySqlFile = container.getPath().resolve(COPY_SQL_FILE);
    	createSqlFile(copySqlFile);

    	// TODO:
    	String password = "";
    	String serverHostName = "";
    	String dbName = "";
    	String user = "";
    	String execCommand = String.format(COPY_EXEC_COMMAND, password, serverHostName, dbName, user);

    	execute(execCommand);

    	container.removeContainer();
	}

	private void createSqlFile(final java.nio.file.Path copySqlFile) {
		for(String filename : fileList) {
            String tableName = filename.substring(0, filename.lastIndexOf('.'));
        	String copyCommand = "\\COPY " + tableName + " FROM " + filename + " WITH CSV encoding 'SJIS'";
    		try (OutputStream outputStream = FileUtil.NoCheck.newOutputStream(copySqlFile)) {
    			try (BufferedWriter bw = new BufferedWriter(
    					new OutputStreamWriter(outputStream, Charset.forName(DEFAULT_ENCODE)))) {
    				bw.write(copyCommand);
    				bw.newLine();
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    			throw new RuntimeException(e);
    		}
    	}
	}

	private boolean execute(String command) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		checkResult(p);

		return true;
	}

	public void checkResult(Process p) {
		if(p == null) {
			throw new IllegalArgumentException();
		}

		InputStream in = null;
		BufferedReader br = null;
		try {
			in = p.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, "Shift_JIS"));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
			in.close();

			in = p.getErrorStream();
			br = new BufferedReader(new InputStreamReader(in, "Shift_JIS"));
			while ((line = br.readLine()) != null) {
				System.err.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
				if (in != null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(p.exitValue() != 0) {
			throw new RuntimeException("command process returned error [" + p.exitValue() + "]");
		};
	}
}
