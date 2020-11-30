package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import java.util.List;

import lombok.Data;

@Data
public class SelectedDelPatternParameterDto {
	
	/**
	 * 選択可能カテゴリ
	 */
	private List<DeleteCategoryDto> selectableCategories;
	
	/**
	 * 選択カテゴリ名称一覧
	 */
	private List<SelectionDelCategoryNameDto> selectedCategories;
}
