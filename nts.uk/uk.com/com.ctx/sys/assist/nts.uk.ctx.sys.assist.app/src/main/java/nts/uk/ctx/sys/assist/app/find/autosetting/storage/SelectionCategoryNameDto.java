package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.sys.assist.app.find.autosetting.AbstractCategoryDto;

/**
 * 選択カテゴリ名称DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SelectionCategoryNameDto extends AbstractCategoryDto {
	
	/**
	 * 保存期間
	 */
	private String retentionPeriod;

	@Builder
	public SelectionCategoryNameDto(String categoryId, String categoryName, int systemType, String retentionPeriod) {
		super(categoryId, categoryName, systemType);
		this.retentionPeriod = retentionPeriod;
	}
}
