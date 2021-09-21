package uk.cnv.client.dom.execute;

import lombok.val;
import uk.cnv.client.UkConvertProperty;

public class ConvertCodeExcecuter {

	public CommandResult doWork() {
		String sqlFileName = UkConvertProperty.getProperty(UkConvertProperty.CONVERT_CODE_FILE);
		val executor = new CommandExecutor();

		return executor.sqlFileExecuteNoDbName(sqlFileName);
	}
}
