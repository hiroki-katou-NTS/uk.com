package nts.uk.ctx.sys.assist.app.command.autosetting;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.app.find.autosetting.CategoryDto;

@Data
public class FindSelectedPatternCommand {
	
	/**
	 * パターンコード
	 */
	private String patternCode;
	
	/**
	 * パターン区分
	 */
	private int patternClassification;
	
	/**
	 * List<カテゴリマスタ＞
	 */
	private List<CategoryDto> categories;
}
