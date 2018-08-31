package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Value;

@Value
public class ObtainRecoveryInfoCommand {
	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;
	/**
	 * データ保存処理ID
	 */
	private String storeProcessingId;
}
