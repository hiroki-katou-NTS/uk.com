package nts.uk.ctx.exio.app.command.exo.executionlog;

import lombok.Value;

@Value
public class ExterOutExecLogCommand {
	
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 外部出力処理ID
	 */
	private String outputProcessId;
	
	/**
	 * ファイルID
	 */
	private String fileId;

}
