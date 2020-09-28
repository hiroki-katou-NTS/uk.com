package nts.uk.ctx.sys.assist.app.command.autosetting;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.sys.assist.app.find.autosetting.CategoryDto;

@Value
public class AddCategoryCommand {
	
	/**
	 * 画面モード
	 */
	private int screenMode;
	
	/**
	 * パータンコード
	 */
	private String patternCode;
	
	/**
	 * パターン名称
	 */
	private String patternName;
	
	/**
	 * 保存するカテゴリ
	 */
	private List<CategoryDto> categories;
}
