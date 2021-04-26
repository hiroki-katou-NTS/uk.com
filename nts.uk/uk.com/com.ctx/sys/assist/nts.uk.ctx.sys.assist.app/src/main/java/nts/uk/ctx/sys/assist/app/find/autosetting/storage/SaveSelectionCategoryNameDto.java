package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.sys.assist.app.find.autosetting.AbstractCategoryDto;

/**
 * 選択カテゴリ名称保存 DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SaveSelectionCategoryNameDto extends AbstractCategoryDto {
	
	/**
	 * 方法指定可能
	 */
	private int specifiedMethod;
	
	/**
	 * 時保存範囲
	 */
	private int storeRange;
	
	/**
	 * 期間区分
	 */
	private int periodDivision;
	
	/**
	 * 別会社区分
	 */
	private int separateCompClassification;
	
	/**
	 * 保存期間
	 */
	private String retentionPeriod;
	
	private TextResourceHolderDto holder;

	@Builder
	public SaveSelectionCategoryNameDto(String categoryId, String categoryName, int systemType, int specifiedMethod,
			int storeRange, int periodDivision, int separateCompClassification, String retentionPeriod, TextResourceHolderDto holder) {
		super(categoryId, categoryName, systemType);
		this.specifiedMethod = specifiedMethod;
		this.storeRange = storeRange;
		this.periodDivision = periodDivision;
		this.separateCompClassification = separateCompClassification;
		this.retentionPeriod = retentionPeriod;
		this.holder = holder;
	}
}
