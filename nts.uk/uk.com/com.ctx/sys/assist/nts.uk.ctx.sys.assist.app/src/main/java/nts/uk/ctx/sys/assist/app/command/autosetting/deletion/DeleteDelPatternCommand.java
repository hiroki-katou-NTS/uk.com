package nts.uk.ctx.sys.assist.app.command.autosetting.deletion;

import lombok.Data;

@Data
public class DeleteDelPatternCommand {
	
	/**
	 * パターン区分
	 */
	private int patternClassification;
	
	/**
	 * パターンコード
	 */
	private String patternCode;
}
