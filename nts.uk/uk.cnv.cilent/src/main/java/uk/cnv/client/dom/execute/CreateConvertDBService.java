package uk.cnv.client.dom.execute;

import lombok.val;
import uk.cnv.client.UkConvertProperty;

public class CreateConvertDBService {
	private static final String CREATE_CONVERT_DB_SCRIPT = "createConvertDbScript";
	private static final String CREATE_UKDB_SCRIPT = "createUkDbScript";

	public boolean doWork() {
		String createConvertDbScript = UkConvertProperty.getProperty(CREATE_CONVERT_DB_SCRIPT);
		String createUkDbScript = UkConvertProperty.getProperty(CREATE_UKDB_SCRIPT);
		val executor = new CommandExecutor();

		System.out.println("移行作業用DB作成　開始");
		boolean result = executor.sqlFileExecuteNoDbName(createConvertDbScript);
		if(!result) {
			return result;
		}
		System.out.println("移行作業用DB作成　終了");

		System.out.println("移行先DB作成　開始");
		result = executor.sqlFileExecuteNoDbName(createUkDbScript);
		System.out.println("移行先DB作成　終了");
		return result;
	}
}
