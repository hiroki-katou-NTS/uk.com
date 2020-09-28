package nts.uk.ctx.sys.assist.app.command.autosetting;

import java.util.List;

import lombok.Value;

@Value
public class SelectCategoryCommand {
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
