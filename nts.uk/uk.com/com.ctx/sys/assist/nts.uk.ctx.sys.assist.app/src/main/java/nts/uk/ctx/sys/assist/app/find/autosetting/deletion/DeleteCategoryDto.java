package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryForDelete;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionSelectionCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCategoryDto implements DataDeletionSelectionCategory.MementoGetter {
	
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
	
	public static DeleteCategoryDto fromDomain(CategoryForDelete domain, int systemType) {
		DeleteCategoryDto dto = new DeleteCategoryDto();
		dto.categoryId = domain.getCategoryId().v();
		dto.categoryName = domain.getCategoryName().v();
		dto.retentionPeriod = domain.getTimeStore().nameId;
		dto.systemType = systemType;
		return dto;
	}
}
