package nts.uk.ctx.exio.app.command.exo.executionlog;

import java.util.Optional;

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

	public ExterOutExecLogCommand(String companyId, String outputProcessId, String fileId) {
		super();
		this.companyId = companyId;
		this.outputProcessId = outputProcessId;
		this.fileId = fileId;
	}
	
}
