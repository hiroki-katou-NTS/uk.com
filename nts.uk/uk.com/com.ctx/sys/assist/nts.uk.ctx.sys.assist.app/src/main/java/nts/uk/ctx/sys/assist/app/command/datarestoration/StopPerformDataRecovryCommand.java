package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Value;

@Value
public class StopPerformDataRecovryCommand {
	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;
}
