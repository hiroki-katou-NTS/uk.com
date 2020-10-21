package nts.uk.ctx.sys.assist.app.command.autosetting.deletion;

import java.util.List;

import lombok.Data;

@Data
public class SelectDelCategoryCommand {
	/**
	 * パターン区分
	 */
	private int patternClassification;
	
	/**
	 * パターンコード
	 */
	private String patternCode;
	
	/**
	 * List<システム種類>
	 */
	private List<Integer> systemType;
}
