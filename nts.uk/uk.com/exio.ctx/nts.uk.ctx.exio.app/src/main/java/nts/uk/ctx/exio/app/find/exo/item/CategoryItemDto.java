package nts.uk.ctx.exio.app.find.exo.item;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;

@Value
public class CategoryItemDto {

	/**
	 * カテゴリ項目NO
	 */
	private int itemNo;
	
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

	public CategoryItemDto(int itemNo, String categoryItemName, int categoryId, int operationSymbol) {
		super();
		this.itemNo = itemNo;
		this.categoryItemName = categoryItemName;
		this.categoryId = categoryId;
		this.operationSymbol = operationSymbol;
	}
	
	public static CategoryItemDto fromDomain(CategoryItem domain) {
		return new CategoryItemDto(domain.getItemNo().v(), "", domain.getCategoryId().v(),
				domain.getOperationSymbol().value);
	}

	public static CategoryItemDto fromDomain(CategoryItem domain, String categoryItemName) {
		return new CategoryItemDto(domain.getItemNo().v(), categoryItemName , domain.getCategoryId().v(),
				domain.getOperationSymbol().value);
	}
}
