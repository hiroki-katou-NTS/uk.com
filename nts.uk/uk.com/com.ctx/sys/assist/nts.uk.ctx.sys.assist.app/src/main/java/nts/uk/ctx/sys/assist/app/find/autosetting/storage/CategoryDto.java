package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.app.command.autosetting.storage.SelectionCategoryNameDto;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategory;

/**
 * カテゴリDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements DataStorageSelectionCategory.MementoGetter {
	
	/**
	 * カテゴリID
	 */
	private String categoryId;
	
	/**
	 * カテゴリ名称
	 */
	private String categoryName;
	
	/**
	 * システム種類
	 */
	private int systemType;
	
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
	
	public boolean equals(Object o) {
		if (o instanceof CategoryDto) {
			return ((CategoryDto) o).categoryId == this.categoryId;
		} else if (o instanceof SelectionCategoryNameDto) {
			return ((SelectionCategoryNameDto) o).getCategoryId() == this.categoryId
					&& ((SelectionCategoryNameDto) o).getSystemType() == this.systemType;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return categoryId.hashCode();
	}
}
