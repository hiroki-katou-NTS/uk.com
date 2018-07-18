package nts.uk.ctx.exio.app.find.exo.item;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;

@Value
public class CategoryItemDto {

	/**
	 * カテゴリ項目NO
	 */
	private int categoryItemNo;
	
	/**
	 * カテゴリ項目名
	 */
	private String categoryItemName;

	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * 演算符号
	 */
	private int operationSymbol;

	public CategoryItemDto(int categoryItemNo, String categoryItemName, int categoryId, int operationSymbol) {
		super();
		this.categoryItemNo = categoryItemNo;
		this.categoryItemName = categoryItemName;
		this.categoryId = categoryId;
		this.operationSymbol = operationSymbol;
	}
	
	public static CategoryItemDto fromDomain(CategoryItem domain) {
		return new CategoryItemDto(domain.getCategoryItemNo().v(), "", domain.getCategoryId().v(),
				domain.getOperationSymbol().value);
	}

	public static CategoryItemDto fromDomain(CategoryItem domain, String categoryItemName) {
		return new CategoryItemDto(domain.getCategoryItemNo().v(), categoryItemName , domain.getCategoryId().v(),
				domain.getOperationSymbol().value);
	}
}
