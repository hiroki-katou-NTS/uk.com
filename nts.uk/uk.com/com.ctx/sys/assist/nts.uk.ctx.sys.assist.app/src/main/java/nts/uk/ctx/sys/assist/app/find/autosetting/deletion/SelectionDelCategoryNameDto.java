package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.sys.assist.app.find.autosetting.AbstractCategoryDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class SelectionDelCategoryNameDto extends AbstractCategoryDto {
	
	/**
	 * 保存期間
	 */
	private String retentionPeriod;

	@Builder
	public SelectionDelCategoryNameDto(String categoryId, String categoryName, int systemType, String retentionPeriod) {
		super(categoryId, categoryName, systemType);
		this.retentionPeriod = retentionPeriod;
	}
}
