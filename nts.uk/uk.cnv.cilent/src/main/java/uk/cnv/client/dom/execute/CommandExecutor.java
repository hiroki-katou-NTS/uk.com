package uk.cnv.client.dom.execute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import uk.cnv.client.UkConvertProperty;

public class CommandExecutor {
	private static final String UKDB_HOST = "UkDbServerHost";
	private static final String UKDB_USER = "UkDbUser";
	private static final String UKDB_PASS = "UkDbPassword";
	private static final String UKDB_DBNAME = "UkDbName";
	private static final String LOG_FILE_PATH = "logfilePath";

	private String host;
	private String user;
	private String password;
	private String dbName;
	private String logfile;

	public CommandExecutor() {
		host = UkConvertProperty.getProperty(UKDB_HOST);
		user = UkConvertProperty.getProperty(UKDB_USER);
		password = UkConvertProperty.getProperty(UKDB_PASS);
		dbName = UkConvertProperty.getProperty(UKDB_DBNAME);
		logfile = UkConvertProperty.getProperty(LOG_FILE_PATH);
	}

	public boolean sqlFileExecuteNoDbName(String fileName) {
		// -r 1 :エラーメッセージ出力を画面にリダイレクト
		// -b :エラー時に中断する
		// -j :エラーメッセージを表示する
		String command = String.format("sqlcmd -S %s -U %s -P %s -i %s -r 1 -b", host, user, password, fileName, logfile);
		return execute(command);
	}

	public boolean sqlFileExecute(String fileName) {
		// -r 1 :エラーメッセージ出力を画面にリダイレクト
		// -b :エラー時に中断する
		// -j :エラーメッセージを表示する
		String command = String.format("sqlcmd -S %s -d %s -U %s -P %s -i %s -r 1 -b", host, dbName, user, password, fileName, logfile);
		return execute(command);
	}

	public boolean bcpExecute(String query, String formatFileName) {
		String command = String.format("bcp table_or_view format nul -c -x -f %s -t, -S %s -U %s -P %s -d %s >> %s  2>&1",
				formatFileName, host, user, password, dbName, logfile);
		return execute(command);
	}

	private boolean execute(String command) {
		String line;
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = input.readLine()) != null) {
				  System.out.println(line);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return (p.exitValue() == 0);
	}

}
