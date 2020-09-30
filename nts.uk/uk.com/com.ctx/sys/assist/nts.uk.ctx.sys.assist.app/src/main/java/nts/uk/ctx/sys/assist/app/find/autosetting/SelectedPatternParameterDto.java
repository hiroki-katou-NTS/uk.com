package nts.uk.ctx.sys.assist.app.find.autosetting;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.app.command.autosetting.SelectionCategoryNameDto;
import nts.uk.ctx.sys.assist.app.find.category.CategoryDto;

@Data
public class SelectedPatternParameterDto {
	
	/**
	 * 選択可能カテゴリ
	 */
	private List<CategoryDto> selectableCategories;
	
	/**
	 * 選択カテゴリ名称一覧
	 */
	private List<SelectionCategoryNameDto> selectedCategories;
}
