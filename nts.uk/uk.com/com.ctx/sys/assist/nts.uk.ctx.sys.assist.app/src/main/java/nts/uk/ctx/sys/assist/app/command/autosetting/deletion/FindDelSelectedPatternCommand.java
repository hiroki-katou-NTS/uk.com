package nts.uk.ctx.sys.assist.app.command.autosetting.deletion;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.DeleteCategoryDto;

@Data
public class FindDelSelectedPatternCommand {
	
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
	private List<DeleteCategoryDto> categories;
}
