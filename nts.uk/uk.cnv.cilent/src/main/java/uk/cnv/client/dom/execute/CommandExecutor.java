package uk.cnv.client.dom.execute;

import java.io.IOException;

import lombok.val;
import uk.cnv.client.LogManager;
import uk.cnv.client.UkConvertProperty;

public class CommandExecutor {
	public String host;
	public String user;
	public String password;
	public String dbName;

	public CommandExecutor() {
		this.host = UkConvertProperty.getProperty(UkConvertProperty.UKDB_HOST);
		this.user = UkConvertProperty.getProperty(UkConvertProperty.UKDB_USER);
		this.password = UkConvertProperty.getProperty(UkConvertProperty.UKDB_PASS);
		this.dbName = UkConvertProperty.getProperty(UkConvertProperty.UKDB_DBNAME);
	}

	public CommandResult sqlFileExecuteNoDbName(String fileName) {
		// -r 1 :エラーメッセージ出力を画面にリダイレクト
		// -b :エラー時に中断する
		// -j :エラーメッセージを表示する
		// -f i:65001 :文字コードをUTF-8（65001）に
		String command = String.format("sqlcmd -S %s -U %s -P %s -i %s -r 1 -b -f i:65001", this.host, this.user, this.password, fileName);
		return execute(command);
	}

	public CommandResult sqlFileExecute(String fileName) {
		// -r 1 :エラーメッセージ出力を画面にリダイレクト
		// -b :エラー時に中断する
		// -j :エラーメッセージを表示する
		// -f i:65001 :文字コードをUTF-8（65001）に
		String command = String.format("sqlcmd -S %s -d %s -U %s -P %s -i %s -r 1 -b -f i:65001", this.host, this.dbName, this.user, this.password, fileName);
		return execute(command);
	}

	public CommandResult bcpExecute(String query, String fileName) {
		// -t, : コンマ区切りファイル
		// -w : Unicode文字を使用（UTF-16で出力される）
		String command = String.format("bcp \"%s\" queryout %s -t, -w -S %s -U %s -P %s -d %s",
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
