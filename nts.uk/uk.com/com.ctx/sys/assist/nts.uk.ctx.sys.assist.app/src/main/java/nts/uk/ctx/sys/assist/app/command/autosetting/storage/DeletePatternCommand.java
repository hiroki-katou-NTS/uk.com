package nts.uk.ctx.sys.assist.app.command.autosetting.storage;

import lombok.Data;

@Data
public class DeletePatternCommand {
	
	/**
	 * パターン区分
	 */
	private int patternClassification;
	
	/**
	 * パターンコード
	 */
	private String patternCode;
}
