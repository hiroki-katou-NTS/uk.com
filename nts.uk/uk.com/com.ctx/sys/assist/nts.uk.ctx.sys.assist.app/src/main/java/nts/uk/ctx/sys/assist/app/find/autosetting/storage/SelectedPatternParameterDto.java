package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import java.util.List;

import lombok.Data;

@Data
public class SelectedPatternParameterDto {
	
	/**
	 * 選択可能カテゴリ
	 */
	private List<CategoryDto> selectableCategories;
	
	/**
	 * 選択カテゴリ名称一覧
	 */
	private DataStoragePatternSettingDto<SelectionCategoryNameDto> pattern;
}
