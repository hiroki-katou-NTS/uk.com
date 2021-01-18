package uk.cnv.client.dom.execute;

import lombok.val;
import uk.cnv.client.UkConvertProperty;

public class ConvertCodeExcecuter {

	private static final String CONVERT_CODE_FILE = "convertCodeFile";

	public CommandResult doWork() {
		String sqlFileName = UkConvertProperty.getProperty(CONVERT_CODE_FILE);
		val executor = new CommandExecutor();

		return executor.sqlFileExecuteNoDbName(sqlFileName);
	}
}
