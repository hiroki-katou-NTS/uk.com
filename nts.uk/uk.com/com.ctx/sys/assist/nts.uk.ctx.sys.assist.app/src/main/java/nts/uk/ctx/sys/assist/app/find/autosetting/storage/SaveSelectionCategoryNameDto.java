package nts.uk.ctx.sys.assist.app.find.autosetting.storage;

import lombok.Data;

/**
 * 選択カテゴリ名称保存 DTO
 */
@Data
public class SaveSelectionCategoryNameDto {
	/**
	 * カテゴリ名称
	 */
	private String categoryName;
	
	/**
	 * カテゴリID
	 */
	private String categoryId;
	
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
	 * システム種類
	 */
	private int systemType;
	
	private TextResourceHolderDto holder;
}
