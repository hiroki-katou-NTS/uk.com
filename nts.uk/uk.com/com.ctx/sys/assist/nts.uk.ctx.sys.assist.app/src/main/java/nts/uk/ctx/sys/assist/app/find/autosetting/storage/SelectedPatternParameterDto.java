package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.assist.app.command.autosetting.storage.SelectionCategoryNameDto;

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
