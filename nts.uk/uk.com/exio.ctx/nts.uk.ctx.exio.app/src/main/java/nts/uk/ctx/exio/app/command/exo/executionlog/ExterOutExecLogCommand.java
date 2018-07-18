package nts.uk.ctx.exio.app.command.exo.executionlog;

import lombok.Value;

@Value
public class ExterOutExecLogCommand {

	/**
	 * 外部出力処理ID
	 */
	private String outputProcessId;
	
	/**
	 * ファイルID
	 */
	private String fileId;

}
