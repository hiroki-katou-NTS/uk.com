package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Data;

@Data
public class CheckPasswordCommand {
	
	/**
	 * データ保存処理ID
	 */
	private String storeProcessingId;
	
	/**
	 * 入るパスワー
	 */
	private String password;
}
