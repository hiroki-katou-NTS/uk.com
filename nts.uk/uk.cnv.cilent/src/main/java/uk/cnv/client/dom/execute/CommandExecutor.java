package uk.cnv.client.dom.execute;

import java.io.IOException;

import lombok.val;
import uk.cnv.client.LogManager;
import uk.cnv.client.UkConvertProperty;

public class CommandExecutor {
	private static final String UKDB_HOST = "UkDbServerHost";
	private static final String UKDB_USER = "UkDbUser";
	private static final String UKDB_PASS = "UkDbPassword";
	private static final String UKDB_DBNAME = "UkDbName";
	//private static final String LOG_FILE_PATH = "logfilePath";
	public String host;
	public String user;
	public String password;
	public String dbName;
	//public String logfile;

	public CommandExecutor() {
		this.host = UkConvertProperty.getProperty(UKDB_HOST);
		this.user = UkConvertProperty.getProperty(UKDB_USER);
		this.password = UkConvertProperty.getProperty(UKDB_PASS);
		this.dbName = UkConvertProperty.getProperty(UKDB_DBNAME);
	//	this.logfile = UkConvertProperty.getProperty(LOG_FILE_PATH);
	}

	public CommandResult sqlFileExecuteNoDbName(String fileName) {
		// -r 1 :エラーメッセージ出力を画面にリダイレクト
		// -b :エラー時に中断する
		// -j :エラーメッセージを表示する
		String command = String.format("sqlcmd -S %s -U %s -P %s -i %s -r 1 -b", this.host, this.user, this.password, fileName);
		return execute(command);
	}

	public CommandResult sqlFileExecute(String fileName) {
		// -r 1 :エラーメッセージ出力を画面にリダイレクト
		// -b :エラー時に中断する
		// -j :エラーメッセージを表示する
		String command = String.format("sqlcmd -S %s -d %s -U %s -P %s -i %s -r 1 -b", this.host, this.dbName, this.user, this.password, fileName);
		return execute(command);
	}

	public CommandResult bcpExecute(String query, String fileName) {
		// -t, : コンマ区切りファイル
		String command = String.format("bcp \"%s\" queryout %s -t, -c -S %s -U %s -P %s -d %s",
				query, fileName, this.host, this.user, this.password, this.dbName);
		return execute(command);
	}

	private CommandResult execute(String command) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			LogManager.err(e);
			val result = new CommandResult(e);
			return result;
		}

		val result = new CommandResult(p);
		result.log();

		return result;
	}
}
