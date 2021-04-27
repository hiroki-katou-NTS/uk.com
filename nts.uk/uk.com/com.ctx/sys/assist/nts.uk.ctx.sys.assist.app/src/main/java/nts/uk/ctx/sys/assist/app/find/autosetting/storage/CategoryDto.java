package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.sys.assist.app.find.autosetting.AbstractCategoryDto;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategory;

/**
 * カテゴリDTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryDto extends AbstractCategoryDto implements DataStorageSelectionCategory.MementoGetter {
	
	/**
	 * パータンコード
	 */
	private String patternCode;
	
	/**
	 * パターン区分
	 */
	private int patternClassification = 0;
	
	/**
	 * 契約コード
	 */
	private String contractCode;
	
	/**
	 * 保存期間区分
	 */
	private String retentionPeriod;
	
	public static CategoryDto fromDomain(Category domain, int systemType) {
		CategoryDto dto = new CategoryDto();
		dto.setCategoryId(domain.getCategoryId().v());
		dto.setCategoryName(domain.getCategoryName().v());
		dto.setRetentionPeriod(domain.getTimeStore().nameId);
		dto.setSystemType(systemType);
		return dto;
	}
}
